package net.tschrock.minecraft.touchmanager;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import org.lwjgl.system.Platform;

import net.tschrock.minecraft.touchcontrols.DebugHelper;
import net.tschrock.minecraft.touchcontrols.DebugHelper.LogLevel;
import net.tschrock.minecraft.touchmanager.drivers.generic.TUIOTouchDriver;
import net.tschrock.minecraft.touchmanager.drivers.linux.X112TUIOTouchDriver;
import net.tschrock.minecraft.touchmanager.drivers.windows.Touch2TUIOTouchDriver;

public class TouchManager {

	protected static ITouchDriver touchDriver = null;
	protected static boolean inited = false;

	private TouchManager() {

	}

	public static String getCurrentTouchDriver() {
		return touchDriver.getClass().getSimpleName();
	}

	public static void init() {
		init(false);
	}

	public static void init(boolean useGeneric) {
		if (!inited)
			touchDriver = createTouchDriver(useGeneric);
		inited = true;
	}

	private static ITouchDriver createTouchDriver() {
		return createTouchDriver(false);
	}

	private static ITouchDriver createTouchDriver(boolean useGeneric) {
		ITouchDriver driver;
		if (useGeneric) {
			driver = new TUIOTouchDriver();
			driver.connect();
		} else {
			switch (Platform.get()) {
			case LINUX:
				driver = new X112TUIOTouchDriver();
				driver.connect();
				break;
			case WINDOWS:
				driver = new Touch2TUIOTouchDriver();
				driver.connect();
				break;
			case MACOSX: // No touch for OSX :P
			default:
				driver = new TUIOTouchDriver();
				driver.connect();
			}
		}
		DebugHelper.log(LogLevel.INFO, "Using '" + driver.getClass().getName() + "' for touch input");
		return driver;
	}

	//
	// Event Queue functions
	//

	public static ArrayList<TouchEvent> getFilteredEvents(TouchEvent.Type type){
		return touchDriver.getFilteredEvents(type);
	}

	public static TouchEvent getNextTouchEvent() {
		return touchDriver.getNextTouchEvent();
	}

	public static TouchEvent glanceNextTouchEvent() {
		return touchDriver.glanceNextTouchEvent();
	}

	public static void clearEventQueue() {
		touchDriver.clearEventQueue();
	}

	public static void enableEventQueue() {
		touchDriver.enableEventQueue();
	}

	public static void disableEventQueue() {
		touchDriver.disableEventQueue();
	}

	public static boolean isEventQueueEnabled() {
		return touchDriver.isEventQueueEnabled();
	}

	//
	// State Polling functions
	//

	public static void resetTouchState() {
		touchDriver.resetTouchState();
	}

	public static List<TouchEvent> pollTouchState() {
		return touchDriver.pollTouchState();
	}

	// Misc

	public static int getXOffset() {
		return touchDriver.hasGlobalFocus() ? Minecraft.getInstance().mainWindow.getWindowX() : 0;
	}

	public static int getYOffset() {
		return touchDriver.hasGlobalFocus() ? Minecraft.getInstance().mainWindow.getWindowY() : 0;
	}
}