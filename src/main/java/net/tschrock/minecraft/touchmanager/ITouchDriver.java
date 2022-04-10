package net.tschrock.minecraft.touchmanager;

import java.util.ArrayList;
import java.util.List;

public abstract interface ITouchDriver {
  public abstract void connect();

  public abstract boolean isNative();

  public abstract boolean hasGlobalFocus();

  public abstract ArrayList<TouchEvent> getFilteredEvents(TouchEvent.Type type);

  public abstract TouchEvent getNextTouchEvent();

  public abstract TouchEvent glanceNextTouchEvent();

  public abstract void clearEventQueue();

  public abstract void enableEventQueue();

  public abstract void disableEventQueue();

  public abstract boolean isEventQueueEnabled();

  public abstract void resetTouchState();

  public abstract List<TouchEvent> pollTouchState();
}
