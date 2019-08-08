package net.tschrock.minecraft.gui.touch;

import net.tschrock.minecraft.touchmanager.TouchEvent;

public abstract interface IMCGuiTouchable
{
  public abstract void onTouchEvent(TouchEvent paramTouchEvent);
}