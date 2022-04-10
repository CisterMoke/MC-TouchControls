package net.tschrock.minecraft.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.tschrock.minecraft.gui.events.MCGuiMouseEvent;
import net.tschrock.minecraft.touchcontrols.DebugHelper;
import net.tschrock.minecraft.touchcontrols.DebugHelper.LogLevel;

public class MCGuiScreen extends Screen implements IMCGuiContainer {

	protected List<MCGuiComponent> components = new ArrayList<MCGuiComponent>();
	protected MCGuiComponent focusedComponent;
	protected MCGuiComponent mouseOverComponent;
	protected int downedButtons = 0;

	public MCGuiScreen(ITextComponent titleIn){
		super(titleIn);
	}

	public void addComponent(MCGuiComponent component) {
		components.add(component);
		component.setParent(this);
		component.setParentScreen(this);
	}

	public void removeComponent(MCGuiComponent component) {
		component.setParent(null);
		component.setParentScreen(null);
		components.remove(component);
	}

	public MCGuiComponent getComponentById(int id) {

		for (MCGuiComponent comp : components) {
			if (comp.id == id) {
				return comp;
			}
		}

		return null;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		for (MCGuiComponent comp : components) {
			comp.draw(minecraft);
		}
	}

	public int[] getXY(double x, double y)
	{
		int xInt = (int) x * this.width / this.minecraft.mainWindow.getWidth();
		int yInt = this.height - (int) y * this.height / this.minecraft.mainWindow.getHeight() - 1;

		return  new int[]{xInt, yInt};
	}

	public void updateMouseOver(double x, double y){
		
		int[] coords = getXY(x, y);
		int xInt = coords[0];
		int yInt = coords[1];

		MCGuiComponent overComponent = getTopComponentAt(xInt, yInt);

		if (overComponent != null && overComponent != focusedComponent) {
			overComponent.onMouseMove(new MCGuiMouseEvent(xInt, yInt));
		}
		if (focusedComponent != null) {
			focusedComponent.onMouseMove(new MCGuiMouseEvent(xInt, yInt));
		}
		if (mouseOverComponent != overComponent) {
			if (mouseOverComponent != null) {
				mouseOverComponent.onMouseOut(new MCGuiMouseEvent(xInt, yInt));
			}
			if (overComponent != null) {
				overComponent.onMouseOver(new MCGuiMouseEvent(xInt, yInt));
			}
		}

		mouseOverComponent = overComponent;
	}

	@Override
	public boolean mouseClicked(double x, double y, int modifier){
		updateMouseOver(x, y);
		int[] coords = getXY(x, y);
		int xInt = coords[0];
		int yInt = coords[1];
		if (downedButtons == 0) {
			DebugHelper.log(LogLevel.DEBUG, "Focus changed to " + mouseOverComponent.getClass().getSimpleName()
					+ "with id=" + mouseOverComponent.getId());
			focusedComponent = mouseOverComponent;
		}
		downedButtons++;
		if (focusedComponent != null) {
			focusedComponent.onMouseDown(new MCGuiMouseEvent(modifier, xInt, yInt));
		}
		return super.mouseClicked(xInt, yInt, modifier);
	}

	@Override
	public boolean mouseReleased(double x, double y, int modifier){
		updateMouseOver(x, y);
		int[] coords = getXY(x, y);
		int xInt = coords[0];
		int yInt = coords[1];
		downedButtons--;
		if (focusedComponent != null) {
			focusedComponent.onMouseUp(new MCGuiMouseEvent(modifier, xInt, yInt));
		}
		if (mouseOverComponent != null && mouseOverComponent == focusedComponent) {
			focusedComponent.onMouseClick(new MCGuiMouseEvent(modifier, xInt, yInt));
		}
		return super.mouseReleased(x, y, modifier);
	}

	@Override
	public void mouseMoved(double x, double y){
		updateMouseOver(x, y);
		super.mouseMoved(x, y);
	}

	// @Override
	// public void handleMouseInput() throws IOException {

	// 	int x = Mouse.getEventX() * this.width / this.minecraft.mainWindow.getWidth();
	// 	int y = this.height - Mouse.getEventY() * this.height / this.minecraft.mainWindow.getHeight() - 1;
	// 	// System.out.println(y);
	// 	int button = Mouse.getEventButton();
	// 	MCGuiComponent overComponent = getTopComponentAt(x, y);

	// 	if (overComponent != null && overComponent != focusedComponent) {
	// 		overComponent.onMouseMove(new MCGuiMouseEvent(x, y));
	// 	}
	// 	if (focusedComponent != null) {
	// 		focusedComponent.onMouseMove(new MCGuiMouseEvent(x, y));
	// 	}
	// 	if (mouseOverComponent != overComponent) {
	// 		if (mouseOverComponent != null) {
	// 			mouseOverComponent.onMouseOut(new MCGuiMouseEvent(x, y));
	// 		}
	// 		if (overComponent != null) {
	// 			overComponent.onMouseOver(new MCGuiMouseEvent(x, y));
	// 		}
	// 	}

	// 	mouseOverComponent = overComponent;

	// 	if (Mouse.getEventButtonState()) // If button was involved and it is pressed (Button down)
	// 	{
	// 		if (downedButtons == 0) {
	// 			DebugHelper.log(LogLevel.DEBUG, "Focus changed to " + overComponent.getClass().getSimpleName()
	// 					+ "with id=" + overComponent.getId());
	// 			focusedComponent = overComponent;
	// 		}
	// 		downedButtons++;
	// 		if (focusedComponent != null) {
	// 			focusedComponent.onMouseDown(new MCGuiMouseEvent(button, x, y));
	// 		}
	// 	} else if (button != -1) // If button was involved and it isn't pressed (Button up)
	// 	{
	// 		downedButtons--;
	// 		if (focusedComponent != null) {
	// 			focusedComponent.onMouseUp(new MCGuiMouseEvent(button, x, y));
	// 		}
	// 		if (overComponent != null && overComponent == focusedComponent) {
	// 			focusedComponent.onMouseClick(new MCGuiMouseEvent(button, x, y));
	// 		}

	// 	}

	// }

	public MCGuiComponent getTopComponentAt(int x, int y) {

		for (int i = components.size() - 1; i >= 0; --i) {
			MCGuiComponent comp = components.get(i);
			if (comp.checkBounds(x, y)) {
				return comp;
			}
		}

		return null;
	}
}