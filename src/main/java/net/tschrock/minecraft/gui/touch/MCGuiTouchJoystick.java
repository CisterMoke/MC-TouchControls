package net.tschrock.minecraft.gui.touch;

import net.tschrock.minecraft.touchmanager.TouchEvent;

public class MCGuiTouchJoystick extends MCGuiTouchComponent {
  protected TouchEvent boundTouch;
  
  public MCGuiTouchJoystick(int id, int x, int y) {
    super(id, x, y);
  }
  
  public MCGuiTouchJoystick(int id, int x, int y, int width, int height) {
    super(id, x, y, width, height);
  }
  
  public MCGuiTouchJoystick(int id, int x, int y, int width, int height, int backgroundColor, int foregroundColor) {
    super(id, x, y, width, height, backgroundColor, foregroundColor);
  }
  



  public void onTouchEvent(TouchEvent event)
  {
    if ((event.getType() == net.tschrock.minecraft.touchmanager.TouchEvent.Type.TOUCH_START) && (this.boundTouch == null)) {
      this.boundTouch = event;
    }
    
    if ((event.getType() == net.tschrock.minecraft.touchmanager.TouchEvent.Type.TOUCH_UPDATE) && (this.boundTouch != null) && (this.boundTouch.getId() == event.getId())) {
      this.boundTouch = event;
    }
    
    if ((event.getType() == net.tschrock.minecraft.touchmanager.TouchEvent.Type.TOUCH_END) && (this.boundTouch != null) && (this.boundTouch.getId() == event.getId())) {
      this.boundTouch = null;
    }
  }
  







  protected void calculateJoystickPosition()
  {
    if (this.boundTouch == null)
    {
      int centeredTouchX = this.boundTouch.getAdjustedX(this.parentScreen.width) - this.preferedX - (this.preferedX + this.preferedWidth / 2);
      int j = this.boundTouch.getAdjustedY(this.parentScreen.height) - this.preferedY - (this.preferedY + this.preferedHeight / 2);
    }
    else {
      int y;
      int i = y = 0;
    }
  }
}