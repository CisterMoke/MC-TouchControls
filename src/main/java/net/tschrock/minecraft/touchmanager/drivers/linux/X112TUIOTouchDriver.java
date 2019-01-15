package net.tschrock.minecraft.touchmanager.drivers.linux;

import net.tschrock.minecraft.touchmanager.BinRunner;
import net.tschrock.minecraft.touchmanager.drivers.generic.TUIOTouchDriver;

public class X112TUIOTouchDriver extends TUIOTouchDriver {
    BinRunner procEx;
    Process proc = null;

    public X112TUIOTouchDriver() {
        Thread closeChildThread = new Thread() {
            public void run() {
                X112TUIOTouchDriver.this.proc.destroy();
            }

        };
        Runtime.getRuntime().addShutdownHook(closeChildThread);

        this.procEx = new BinRunner("bin/x112tuio");
        this.proc = this.procEx.extractAndRun();
    }
}
