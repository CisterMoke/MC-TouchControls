package net.tschrock.minecraft.gui.touch;

import net.tschrock.minecraft.gui.MCGuiComponent;

public class EmulatedMouseAction
{
  protected net.tschrock.minecraft.touchmanager.TouchEvent startTouchEvent;
  protected Long startTime;
  protected MCGuiComponent startComponent;
  protected MCGuiComponent overComponent;
  
  public static enum ActionType {
    UNDETERMINED,  CLICK,  DRAG;
    
    private ActionType() {} }
  
  public static enum ClickType { UNDETERMINED,  LEFT,  RIGHT;
    
    private ClickType() {} }
  protected ActionType actionType = ActionType.UNDETERMINED;
  protected ClickType clickType = ClickType.UNDETERMINED;
  
  public net.tschrock.minecraft.touchmanager.TouchEvent getStartTouchEvent() {
    return this.startTouchEvent;
  }
  
  public MCGuiComponent getOverComponent() {
    return this.overComponent;
  }
  
  public void setOverComponent(MCGuiComponent overComponent) {
    this.overComponent = overComponent;
  }
  
  public Long getStartTime() {
    return this.startTime;
  }
  
  public MCGuiComponent getStartComponent() {
    return this.startComponent;
  }
  
  public ActionType getActionType() {
    return this.actionType;
  }
  
  public void setActionType(ActionType actionType) {
    this.actionType = actionType;
  }
  
  public ClickType getClickType() {
    return this.clickType;
  }
  
  public void setClickType(ClickType clickType) {
    this.clickType = clickType;
  }
  
  public EmulatedMouseAction(net.tschrock.minecraft.touchmanager.TouchEvent startTouchEvent, Long startTime, MCGuiComponent startComponent, MCGuiComponent overComponent) {
    this.startTouchEvent = startTouchEvent;
    this.startTime = startTime;
    this.startComponent = startComponent;
    this.overComponent = overComponent;
  }
}