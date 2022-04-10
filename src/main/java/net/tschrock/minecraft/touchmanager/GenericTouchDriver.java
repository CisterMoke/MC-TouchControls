package net.tschrock.minecraft.touchmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.tschrock.minecraft.touchcontrols.DebugHelper;

public abstract class GenericTouchDriver extends Thread implements ITouchDriver {
    protected boolean queueEnabled = false;

    protected ConcurrentLinkedQueue<TouchEvent> eventQueue = new ConcurrentLinkedQueue();

    protected List<TouchEvent> currentTouchState = new ArrayList();

    protected int findIndexByTouchId(int id) {
        for (int i = 0; i < this.currentTouchState.size(); i++) {
            if (((TouchEvent) this.currentTouchState.get(i)).touchId == id) {
                return i;
            }
        }
        return -1;
    }

    protected void onNewEvent(TouchEvent event) {
        if (this.queueEnabled) {
            this.eventQueue.add(event);
        }

        if (event.touchType == TouchEvent.Type.TOUCH_START) {
            DebugHelper.log(DebugHelper.LogLevel.DEBUG1,
                    "Got 'TOUCH_START' at (" + event.getX() + ", " + event.getY() + ") with id=" + event.getId());
            this.currentTouchState.add(event);
        } else if (event.touchType == TouchEvent.Type.TOUCH_UPDATE) {
            DebugHelper.log(DebugHelper.LogLevel.DEBUG2,
                    "Got 'TOUCH_UPDATE' at (" + event.getX() + ", " + event.getY() + ") with id=" + event.getId());
            int index = findIndexByTouchId(event.touchId);
            if (index != -1) {
                this.currentTouchState.set(index, event);
            } else {
                DebugHelper.log(DebugHelper.LogLevel.WARNING,
                        "'TOUCH_UPDATE' with id=" + event.getId() + " has no matching start event!");
            }
        } else if (event.touchType == TouchEvent.Type.TOUCH_END) {
            DebugHelper.log(DebugHelper.LogLevel.DEBUG1,
                    "Got 'TOUCH_END' at (" + event.getX() + ", " + event.getY() + ") with id=" + event.getId());
            int index = findIndexByTouchId(event.touchId);
            if (index != -1) {
                this.currentTouchState.remove(index);
            } else {
                DebugHelper.log(DebugHelper.LogLevel.WARNING,
                        "'TOUCH_END' with id=" + event.getId() + " has no matching start event!");
            }
        }
    }

    @Override
    public ArrayList<TouchEvent> getFilteredEvents(TouchEvent.Type type){
        ArrayList<TouchEvent> filtered = new ArrayList<TouchEvent>();
        TouchEvent nextEvent;
        ConcurrentLinkedQueue<TouchEvent> newQueue = new ConcurrentLinkedQueue<TouchEvent>();
        while ((nextEvent = this.eventQueue.poll()) != null){
            if (nextEvent.touchType == type){
                filtered.add(nextEvent);
            }
            else {
                newQueue.add(nextEvent);
            }
        }
        eventQueue = newQueue;
        return filtered;
    }

    public TouchEvent getNextTouchEvent() {
        return (TouchEvent) this.eventQueue.poll();
    }

    public TouchEvent glanceNextTouchEvent() {
        return (TouchEvent) this.eventQueue.peek();
    }

    public void clearEventQueue() {
        this.eventQueue.clear();
    }

    public void enableEventQueue() {
        clearEventQueue();
        this.queueEnabled = true;
        System.out.println("Queue Enabled");
    }

    public void disableEventQueue() {
        this.queueEnabled = false;
        clearEventQueue();
        System.out.println("Queue Disabled");
    }

    public boolean isEventQueueEnabled() {
        return this.queueEnabled;
    }

    public void resetTouchState() {
        this.currentTouchState.clear();
    }

    public List<TouchEvent> pollTouchState() {
        return new ArrayList(this.currentTouchState);
    }
}
