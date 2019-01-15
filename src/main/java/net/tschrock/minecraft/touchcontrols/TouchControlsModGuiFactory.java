package net.tschrock.minecraft.touchcontrols;

import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

public class TouchControlsModGuiFactory implements IModGuiFactory {
	public void initialize(Minecraft minecraftInstance) {
	}

	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return TouchControlsModConfigGUI.class;
	}

	public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	@Override
	public boolean hasConfigGui() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		// TODO Auto-generated method stub
		return null;
	}
}
