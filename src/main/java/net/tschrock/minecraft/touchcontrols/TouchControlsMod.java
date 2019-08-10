package net.tschrock.minecraft.touchcontrols;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.tschrock.minecraft.touchcontrols.DebugHelper.LogLevel;
import net.tschrock.minecraft.touchmanager.TouchManager;

@Mod(modid = TouchControlsMod.MODID, version = TouchControlsMod.VERSION, guiFactory = "net.tschrock.minecraft.touchcontrols.TouchControlsModGuiFactory")
public class TouchControlsMod {

	public static final String MODID = "touchcontrols";
	public static final String VERSION = "0.0.4";

	protected FMLEventHandler fmlEventHandler;
	protected ForgeEventHandler forgeEventHandler;
	protected KeyHandler keyHandler;

	@Mod.Instance("touchcontrols")
	public static TouchControlsMod instance;

	protected static Configuration configFile;

	public static boolean config_invertX = false;
	public static boolean config_invertY = false;

	public static int config_dPadSize = 30;
	public static int config_dPadOpacity = 85;

	public static int config_sensitivity = 50;

	public static int config_leftClickTimeout = 500;
	public static int config_rightClickTimeout = 800;
	public static int config_dragStartRadius = 10;

	public static int config_xOffset = 0;
	public static int config_yOffset = 0;

	public static boolean config_customTuio = false;
	public static boolean config_debug = false;

	public static String file_host = "https://www.altillimity.com";
	public static String file_url = file_host + "/tuio.zip";

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		configFile = new Configuration(event.getSuggestedConfigurationFile());
		syncConfig();

		if (config_debug) {
			File currentDir = new File(".");
			File debugFile = new File(currentDir, "TouchControls.log");

			try {
				DebugHelper.logStream = new PrintStream(debugFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			DebugHelper.logLevel = LogLevel.DEBUG2;
			DebugHelper.enable();

			DebugHelper.logSystemInfo();
		}

		TouchManager.init(config_customTuio);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		DebugHelper.logMinecraftInfo();
		fmlEventHandler = new FMLEventHandler(this);
		forgeEventHandler = new ForgeEventHandler(this);
		// keyHandler = new KeyHandler(this);

		MinecraftForge.EVENT_BUS.register(forgeEventHandler);
		FMLCommonHandler.instance().bus().register(fmlEventHandler);
		// FMLCommonHandler.instance().bus().register(keyHandler);

		System.out.println("Checking required executables");
		if (!new File("config/touchcontrols").exists()) {
			System.out.println("Not found!");
			if (isServerReachable()) {
				try {
					System.out.println("Downloading required files...");
					File zip_file = new File("config/touchcontrols/tuio.zip");
					FileUtils.copyURLToFile(new URL(file_url), zip_file, 10000, 10000);
					ZipFile zipFile = new ZipFile(zip_file);
					System.out.println("Extracting...");
					zipFile.extractAll(new File("config/touchcontrols").getAbsolutePath());
					System.out.println("Cleaning...");
					zip_file.delete();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ZipException e) {
					e.printStackTrace();
				}
			} else {
				Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(new IOException(),
						"Can't reach TouchControls' download server and the files needs to be downloaded! Check your internet connection."));
			}
		} else {
			System.out.println("Found!");
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		DebugHelper.logTouchAndWindowInfo();
	}

	public void toggleTouchMode() {
		Minecraft.getMinecraft().gameSettings.touchscreen = !Minecraft.getMinecraft().gameSettings.touchscreen;
	}

	public void setTouchMode(boolean enabled) {
		Minecraft.getMinecraft().gameSettings.touchscreen = enabled;
	}

	public boolean getTouchMode() {
		return Minecraft.getMinecraft().gameSettings.touchscreen;
	}

	public void syncConfig() {

		String cat = Configuration.CATEGORY_GENERAL;
		int intMin = Integer.MIN_VALUE;
		int intMax = Integer.MAX_VALUE;

		config_xOffset = configFile.getInt("Touch Offset X", cat, config_xOffset, intMin, intMax,
				"Touch offset for the X-Axis.");
		config_yOffset = configFile.getInt("Touch Offset Y", cat, config_yOffset, intMin, intMax,
				"Touch offset for the Y-Axis.");

		config_invertX = configFile.getBoolean("Invert X-Axis", cat, config_invertX, "Inverts the X-Axis.");
		config_invertY = configFile.getBoolean("Invert Y-Axis", cat, config_invertY, "Inverts the Y-Axis.");
		config_sensitivity = configFile.getInt("Movement Sensitivity", cat, config_sensitivity, 0, 100,
				"The sensitivity of touch movement.");

		config_dPadSize = configFile.getInt("D-Pad Size", cat, config_dPadSize, 10, 100,
				"The size of the D-pad on the screen.");
		config_dPadOpacity = configFile.getInt("D-Pad Opacity", cat, config_dPadOpacity, 50, 100,
				"The opacity of the D-pad on the screen.");

		config_leftClickTimeout = configFile.getInt("Timeout - Left Click", cat, config_leftClickTimeout, 100, 5000,
				"The max amount of time to wait for an emulated left click.");
		config_rightClickTimeout = configFile.getInt("Timeout - Right Click", cat, config_rightClickTimeout, 200, 5000,
				"The amount of time to wait for a touch before it's considered a right click.");
		config_dragStartRadius = configFile.getInt("Drag Start Radius", cat, config_dragStartRadius, 0, 100,
				"The amount of leeway given to a touch before it's considered a drag.");

		config_customTuio = configFile.getBoolean("(Dev) Use Custom TUIO Server", cat, config_customTuio,
				"Allows you to run your own TUIO output bridge instead of the built-in one. Requires restart.");
		config_debug = configFile.getBoolean("Debug Mode", cat, config_invertY,
				"Saves a debug log in .minecraft/TouchControls.log.");

		if (configFile.hasChanged())
			configFile.save();
		DebugHelper.logModConfig();
	}

	/**
	 * Allows checking if the server used to host our executables is reachable.
	 * 
	 * @return
	 */
	public static boolean isServerReachable() {
		try {
			final URL url = new URL(file_host);
			final URLConnection conn = url.openConnection();
			conn.connect();
			conn.getInputStream().close();
			return true;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			return false;
		}
	}

}