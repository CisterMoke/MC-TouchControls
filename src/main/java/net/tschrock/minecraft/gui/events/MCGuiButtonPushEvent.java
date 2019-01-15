package net.tschrock.minecraft.gui.events;

import net.tschrock.minecraft.gui.MCGuiButton;

public class MCGuiButtonPushEvent {
    protected MCGuiButton button;
    protected int mouseX;
    protected int mouseY;

    public MCGuiButton getButton() {
        return this.button;
    }

    public int getMouseX() {
        return this.mouseX;
    }

    public int getMouseY() {
        return this.mouseY;
    }

    public MCGuiButtonPushEvent(MCGuiButton mcGuiButton, int mouseX, int mouseY) {
        this.button = mcGuiButton;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }
}