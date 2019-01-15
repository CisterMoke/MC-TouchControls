package net.tschrock.minecraft.gui;

import net.tschrock.minecraft.gui.events.MCGuiMouseEvent;

public class MCGuiToggleButton extends MCGuiButton {
    public MCGuiToggleButton(int id, String text, int x, int y) {
        super(id, text, x, y);
    }

    public MCGuiToggleButton(int id, String text, int x, int y, int width, int height) {
        super(id, text, x, y, width, height);
    }

    protected boolean selected = false;

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void toggle() {
        this.selected = (!this.selected);
    }

    public void onMouseClick(MCGuiMouseEvent event) {
        if (checkBounds(event.getX(), event.getY())) {
            toggle();
            super.onMouseClick(event);
        }
    }

    public <T> T getStateValue(T onDisabled, T onMouseDown, T onMouseOver, T onDfault) {
        return (this.mouseOver) || (this.selected) ? onMouseOver
                : this.mouseDown ? onMouseDown : !this.enabled ? onDisabled : onDfault;
    }
}
