package net.tschrock.minecraft.gui.events;

import net.tschrock.minecraft.gui.touch.MCGuiTouchPad;

public class TouchPadEvent {
    protected MCGuiTouchPad touchPad;
    protected net.tschrock.minecraft.gui.touch.TrackedTouchEvent touchEvent;
    protected Type type;

    public TouchPadEvent(MCGuiTouchPad touchPad, net.tschrock.minecraft.gui.touch.TrackedTouchEvent touchEvent,
            Type type) {
        this.touchPad = touchPad;
        this.touchEvent = touchEvent;
        this.type = type;
    }

    public static enum Type {
        TAP, HOLD_START, HOLD_END, DRAG_START, DRAG_UPDATE, DRAG_END;

        private Type() {
        }
    }

    public net.tschrock.minecraft.gui.touch.TrackedTouchEvent getTouchEvent() {
        return this.touchEvent;
    }

    public Type getType() {
        return this.type;
    }

    public MCGuiTouchPad getTouchPad() {
        return this.touchPad;
    }
}
