package net.tschrock.minecraft.gui;

import net.minecraft.util.text.ITextComponent;
import net.tschrock.minecraft.gui.touch.MCGuiTouchPad;
import net.tschrock.minecraft.gui.touch.MCGuiTouchScreen;
import net.tschrock.minecraft.touchmanager.TouchManager;

public class MCGuiTestScreen extends MCGuiTouchScreen {

	public MCGuiTestScreen(ITextComponent titleIn){
		super(titleIn);
	}

	@Override
	public void init() {
		TouchManager.enableEventQueue();
		addComponent(new MCGuiLabel(0, "Hello World", 20, 20, 200, 20));
		addComponent(new MCGuiButton(1, "Hello World", 20, 45, 200, 20));
		addComponent(new MCGuiTouchPad(2, 20, 70, 400, 100));
	}

	@Override
	public void onClose() {
		TouchManager.disableEventQueue();
	}

}