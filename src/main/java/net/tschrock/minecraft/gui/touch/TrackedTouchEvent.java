package net.tschrock.minecraft.gui.touch;

import net.tschrock.minecraft.touchmanager.TouchEvent;

public class TrackedTouchEvent {
    protected TouchEvent startEvent;
    protected TouchEvent lastEvent;

    public TrackedTouchEvent(TouchEvent startEvent) {
        this.startEvent = startEvent;
        this.lastEvent = startEvent;
        this.dX = 0;
        this.dY = 0;
    }

    protected int dX;

    protected int dY;

    public static enum TapType {
        UNDETERMINED, TAP, HOLD, DRAG, HOLDDRAG;

        private TapType() {
        }
    }

    protected TapType tapType = TapType.UNDETERMINED;

    public TapType getClickType() {
        return this.tapType;
    }

    public void setClickType(TapType tapType) {
        this.tapType = tapType;
    }

    public TouchEvent getStartEvent() {
        return this.startEvent;
    }

    public TouchEvent getLastEvent() {
        return this.lastEvent;
    }

    public void addNewEvent(TouchEvent event) {
        if (event.getId() == this.startEvent.getId()) {
            this.dX += event.getX() - this.lastEvent.getX();
            this.dY += event.getY() - this.lastEvent.getY();
            this.lastEvent = event;
        }
    }

    public int getDX() {
        int tmpDX = this.dX;
        this.dX = 0;
        return tmpDX;
    }

    public int getDY() {
        int tmpDY = this.dY;
        this.dY = 0;
        return tmpDY;
    }
}
