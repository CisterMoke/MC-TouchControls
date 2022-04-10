package net.tschrock.minecraft.touchmanager.drivers.windows;

import net.tschrock.minecraft.touchmanager.BinRunner;
import net.tschrock.minecraft.touchmanager.drivers.generic.TUIOTouchDriver;

public class Touch2TUIOTouchDriver extends TUIOTouchDriver {
    BinRunner procEx;
    // BinRunner procDll;
    Process proc = null;

    public boolean hasGlobalFocus() {
        return true;
    }

    public Touch2TUIOTouchDriver() {
        Thread closeChildThread = new Thread() {
            public void run() {
                Touch2TUIOTouchDriver.this.proc.destroy();
            }

        };
        Runtime.getRuntime().addShutdownHook(closeChildThread);

        boolean is64bit = false;
        if (System.getProperty("os.name").contains("Windows")) {
            is64bit = System.getenv("ProgramFiles(x86)") != null;
        } else {
            is64bit = System.getProperty("os.arch").indexOf("64") != -1;
        }

        if (is64bit) {
            // this.procDll = new BinRunner("config/touchcontrols/TouchHook_x64.dll");
            this.procEx = new BinRunner("config/touchcontrols/Touch2Tuio_x64.exe", " Minecraft");
        } else {
            // this.procDll = new BinRunner("config/touchcontrols/TouchHook_x64.dll");
            this.procEx = new BinRunner("config/touchcontrols/Touch2Tuio_x64.exe", " Minecraft");
        }
        System.out.println("Running Touch2Tuio" + (is64bit ? "_x64.exe" : ".exe"));
        this.proc = this.procEx.run();
    }
}
