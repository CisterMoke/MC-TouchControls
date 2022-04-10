package net.tschrock.minecraft.touchmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.tschrock.minecraft.touchcontrols.DebugHelper;

public class BinRunner {
    BinRunner self;
    String internalLocation;
    String commandArguments;
    String extractedLocation;
    Process binProcess;

    public class InputStreamLogger implements Runnable {
        String prefix = "";
        InputStream stream = null;
        BufferedReader br = null;
        boolean running = false;
        DebugHelper.LogLevel logLevel = DebugHelper.LogLevel.DEBUG;

        public InputStreamLogger(DebugHelper.LogLevel logLevel, String prefix, InputStream stream) {
            this.logLevel = logLevel;
            this.prefix = prefix;
            this.stream = stream;
        }

        public void run() {
            this.running = true;
            try {
                this.br = new BufferedReader(new InputStreamReader(this.stream, "UTF-8"));
                String sCurrentLine;
                while (((sCurrentLine = this.br.readLine()) != null) && (this.running)) {
                    DebugHelper.log(this.logLevel, this.prefix + sCurrentLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public BinRunner(String extractedLocation) {
        this(extractedLocation, "");
    }

    public BinRunner(String extractedLocation, String commandArguments) {
        this.extractedLocation = extractedLocation;
        this.commandArguments = commandArguments;

        this.self = this;
    }

    boolean running = false;
    public boolean logError = true;
    public boolean logOutput = true;

    public Process run() {
        if (this.running) {
            stop();
        }
        try {
            new File(extractedLocation).setExecutable(true);
            this.binProcess = Runtime.getRuntime().exec(this.extractedLocation + " " + this.commandArguments);
            this.running = true;
            DebugHelper.log(DebugHelper.LogLevel.NOTE,
                    "Started '" + this.internalLocation + "' at '" + this.extractedLocation + "'");
            if (this.logOutput) {
                new Thread(new InputStreamLogger(DebugHelper.LogLevel.NOTE,
                        "'" + this.extractedLocation + " " + this.commandArguments + "': ",
                        this.binProcess.getInputStream())).start();
            }
            if (this.logError) {
                new Thread(new InputStreamLogger(DebugHelper.LogLevel.ERROR,
                        "'" + this.extractedLocation + " " + this.commandArguments + "': ",
                        this.binProcess.getErrorStream())).start();
            }
        } catch (IOException e) {
            DebugHelper.log(DebugHelper.LogLevel.ERROR, "Error running '" + this.internalLocation + "' at '"
                    + this.extractedLocation + " " + this.commandArguments + "':");
            DebugHelper.printTrace(DebugHelper.LogLevel.ERROR, e);
        }

        return this.binProcess;
    }

    public void stop() {
        this.binProcess.destroy();
        this.running = false;
    }
}
