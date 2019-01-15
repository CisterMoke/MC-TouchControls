package net.tschrock.minecraft.gui;

public abstract interface IMCGuiContainer {
  public abstract void addComponent(MCGuiComponent paramMCGuiComponent);

  public abstract void removeComponent(MCGuiComponent paramMCGuiComponent);

  public abstract MCGuiComponent getComponentById(int paramInt);

  public abstract MCGuiComponent getTopComponentAt(int paramInt1, int paramInt2);
}
