package net.minecraft.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.screen.Screen;

public class GuiScreenHack{

	public static void callKeyTyped(Screen screen, char typedChar, int keyCode) throws IOException {
		screen.charTyped(typedChar, keyCode);
	}
	
}