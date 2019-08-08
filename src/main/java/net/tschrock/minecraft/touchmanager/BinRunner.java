package net.tschrock.minecraft.touchmanager;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import net.tschrock.minecraft.touchcontrols.DebugHelper;
import net.tschrock.minecraft.touchcontrols.DebugHelper.LogLevel;











public class BinRunner
{
  BinRunner self;
  String internalLocation;
  String commandArguments;
  String extractedLocation;
  Process binProcess;
  
  public class InputStreamLogger
    implements Runnable
  {
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
    

    public void run()
    {
      this.running = true;
      try {
        this.br = new BufferedReader(new InputStreamReader(this.stream, "UTF-8"));
        String sCurrentLine; while (((sCurrentLine = this.br.readLine()) != null) && (this.running)) {
          DebugHelper.log(this.logLevel, this.prefix + sCurrentLine);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  


  public BinRunner(String internalLocation)
  {
    this(internalLocation, "");
  }
  
  public BinRunner(String internalLocation, String commandArguments) {
    this.internalLocation = internalLocation;
    this.commandArguments = commandArguments;
    
    this.self = this;
  }
  




  boolean extracted = false;
  boolean running = false;
  public boolean logError = true;
  public boolean logOutput = true;
  
  public void extract() {
    if (this.extracted) {
      cleanup();
    }
    try
    {
      this.extractedLocation = extractResource(this.internalLocation);
      this.extracted = true;
      DebugHelper.log(DebugHelper.LogLevel.INFO, "Extracted '" + this.internalLocation + "' to '" + this.extractedLocation + "'");
    } catch (IOException e) {
      DebugHelper.log(DebugHelper.LogLevel.ERROR, "Error extracting '" + this.internalLocation + "':");
      DebugHelper.printTrace(DebugHelper.LogLevel.ERROR, e);
    }
  }
  
  public Process run()
  {
    if (!this.extracted) {
      extract();
    }
    if (this.running) {
      stop();
    }
    try
    {
      this.binProcess = Runtime.getRuntime().exec(this.extractedLocation + " " + this.commandArguments);
      this.running = true;
      DebugHelper.log(DebugHelper.LogLevel.NOTE, "Started '" + this.internalLocation + "' at '" + this.extractedLocation + "'");
      if (this.logOutput) {
        new Thread(new InputStreamLogger(DebugHelper.LogLevel.NOTE, "'" + this.extractedLocation + " " + this.commandArguments + "': ", this.binProcess.getInputStream())).start();
      }
      if (this.logError) {
        new Thread(new InputStreamLogger(DebugHelper.LogLevel.ERROR, "'" + this.extractedLocation + " " + this.commandArguments + "': ", this.binProcess.getErrorStream())).start();
      }
    } catch (IOException e) {
      DebugHelper.log(DebugHelper.LogLevel.ERROR, "Error running '" + this.internalLocation + "' at '" + this.extractedLocation + " " + this.commandArguments + "':");
      DebugHelper.printTrace(DebugHelper.LogLevel.ERROR, e);
    }
    
    return this.binProcess;
  }
  
  public Process extractAndRun() {
    extract();
    return run();
  }
  
  public void stop() {
    this.binProcess.destroy();
    this.running = false;
  }
  
  public void cleanup() {
    try {
      Files.deleteIfExists(Paths.get(this.extractedLocation, new String[0]));
    } catch (IOException e) {
      DebugHelper.log(DebugHelper.LogLevel.WARNING, "IOExeption while cleaning up '" + this.extractedLocation + "'");
      DebugHelper.printTrace(DebugHelper.LogLevel.WARNING, e);
    }
    this.extracted = false;
  }
  
  private static void close(Closeable stream) {
    if (stream != null) {
      try {
        stream.close();
      } catch (IOException e) {
        DebugHelper.log(DebugHelper.LogLevel.WARNING, "IOExeption while closing stream:");
        DebugHelper.printTrace(DebugHelper.LogLevel.WARNING, e);
      }
    }
  }
  
  private String extractResource(String resourceLocation) throws IOException {
    InputStream fileInStream = getClass().getClassLoader().getResourceAsStream(resourceLocation);
    File tempFile = File.createTempFile(new File(resourceLocation).getName(), "");
    tempFile.deleteOnExit();
    tempFile.setExecutable(true);
    OutputStream fileOutStream = new FileOutputStream(tempFile);
    


    try
    {
      byte[] buf = new byte['Ѐ'];
      int i = 0;
      
      while ((i = fileInStream.read(buf)) != -1) {
        fileOutStream.write(buf, 0, i);
      }
    } finally {
      close(fileInStream);
      close(fileOutStream);
    }
    return tempFile.getAbsolutePath();
  }
}