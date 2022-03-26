package net.tschrock.minecraft.touchmanager.drivers.generic;

import TUIO.TuioBlob;
import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioObject;
import TUIO.TuioTime;
import java.awt.Dimension;
import java.awt.Toolkit;
import net.tschrock.minecraft.touchmanager.TouchEvent;

public class TUIOTouchDriver extends net.tschrock.minecraft.touchmanager.GenericTouchDriver
        implements TUIO.TuioListener {
    protected TuioClient tClient;

    public TUIOTouchDriver() {
        this.tClient = new TuioClient();
        this.tClient.addTuioListener(this);
    }

    public boolean isNative() {
        return false;
    }

    public boolean hasGlobalFocus() {
        return true;
    }

    public void connect() {
        System.out.println("Connecting TUIOClient...");
        this.tClient.connect();
        System.out.println("Done!");
    }

    public void addTuioBlob(TuioBlob arg0) {
    }

    public void addTuioCursor(TuioCursor tCursor) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        onNewEvent(new TouchEvent(TouchEvent.Type.TOUCH_START, tCursor.getCursorID(), tCursor.getScreenX(width),
                tCursor.getScreenY(height)));
    }

    public void addTuioObject(TuioObject arg0) {
    }

    public void refresh(TuioTime arg0) {
    }

    public void removeTuioBlob(TuioBlob arg0) {
    }

    public void removeTuioCursor(TuioCursor tCursor) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        onNewEvent(new TouchEvent(TouchEvent.Type.TOUCH_END, tCursor.getCursorID(), tCursor.getScreenX(width),
                tCursor.getScreenY(height)));
    }

    public void removeTuioObject(TuioObject arg0) {
    }

    public void updateTuioBlob(TuioBlob arg0) {
    }

    public void updateTuioCursor(TuioCursor tCursor) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        onNewEvent(new TouchEvent(TouchEvent.Type.TOUCH_UPDATE, tCursor.getCursorID(), tCursor.getScreenX(width),
                tCursor.getScreenY(height)));
    }

    public void updateTuioObject(TuioObject arg0) {
    }
}
