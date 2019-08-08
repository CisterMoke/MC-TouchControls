package net.tschrock.minecraft.gui.events;

public class MCGuiMouseEvent { protected int mouseX;
  protected int mouseY;
  
  public MCGuiMouseEvent(int mouseX, int mouseY) { this(-1, mouseX, mouseY, false); }
  
  public MCGuiMouseEvent(int button, int mouseX, int mouseY)
  {
    this(button, mouseX, mouseY, false);
  }
  
  public MCGuiMouseEvent(int button, int mouseX, int mouseY, boolean emulated) {
    this.button = button;
    this.mouseX = mouseX;
    this.mouseY = mouseY;
    this.emulated = emulated;
  }
  

  protected int button;
  
  protected boolean emulated;
  public int getX()
  {
    return this.mouseX;
  }
  
  public int getY() { return this.mouseY; }
  
  public int getButton() {
    return this.button;
  }
  
  public boolean isEmulated() { return this.emulated; }
}