package net.tschrock.minecraft.touchcontrols;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class TouchControlsModConfigGUI extends GuiConfig {
    public TouchControlsModConfigGUI(net.minecraft.client.gui.GuiScreen parent) {
        super(parent,
                new net.minecraftforge.common.config.ConfigElement(TouchControlsMod.configFile.getCategory("general"))
                        .getChildElements(),
                "touchcontrols", false, false, GuiConfig.getAbridgedConfigPath(TouchControlsMod.configFile.toString()));
    }
}
