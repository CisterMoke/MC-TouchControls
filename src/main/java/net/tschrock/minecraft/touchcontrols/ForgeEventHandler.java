package net.tschrock.minecraft.touchcontrols;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenHack;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.tschrock.minecraft.gui.MCGuiButton;
import net.tschrock.minecraft.gui.MCGuiComponent;
import net.tschrock.minecraft.gui.MCGuiTestScreen;
import net.tschrock.minecraft.gui.events.IMCGuiButtonPushListener;
import net.tschrock.minecraft.gui.events.MCGuiButtonPushEvent;
import net.tschrock.minecraft.gui.events.MCGuiMouseEvent;
import net.tschrock.minecraft.gui.touch.EmulatedMouseAction;
import net.tschrock.minecraft.gui.touch.MCGuiTouchComponent;
import net.tschrock.minecraft.gui.touch.MCGuiTouchDPad;
import net.tschrock.minecraft.gui.touch.EmulatedMouseAction.ActionType;
import net.tschrock.minecraft.gui.touch.EmulatedMouseAction.ClickType;
import net.tschrock.minecraft.touchcontrols.DebugHelper.LogLevel;
import net.tschrock.minecraft.touchmanager.TouchEvent;
import net.tschrock.minecraft.touchmanager.TouchManager;
import net.tschrock.minecraft.touchmanager.TouchEvent.Type;

public class ForgeEventHandler implements IMCGuiButtonPushListener {

	private TouchControlsMod homeMod;
	private boolean oldCanDraw = false;
	private boolean oldTouchMode = false;
	protected GuiTouchOverlay overlay;

	public static int width;
	public static int height;

	MCGuiButton escapeBtn;
	MCGuiButton sendBtn;
	MCGuiButton slashBtn;
	MCGuiButton spaceBtn;
	MCGuiButton backBtn;
	MCGuiButton tabBtn;
	MCGuiButton capsBtn;
	MCGuiButton aBtn;
	MCGuiButton bBtn;
	MCGuiButton cBtn;
	MCGuiButton dBtn;
	MCGuiButton eBtn;
	MCGuiButton fBtn;
	MCGuiButton gBtn;
	MCGuiButton hBtn;
	MCGuiButton iBtn;
	MCGuiButton jBtn;
	MCGuiButton kBtn;
	MCGuiButton lBtn;
	MCGuiButton mBtn;
	MCGuiButton nBtn;
	MCGuiButton oBtn;
	MCGuiButton pBtn;
	MCGuiButton qBtn;
	MCGuiButton rBtn;
	MCGuiButton sBtn;
	MCGuiButton tBtn;
	MCGuiButton uBtn;
	MCGuiButton vBtn;
	MCGuiButton wBtn;
	MCGuiButton xBtn;
	MCGuiButton yBtn;
	MCGuiButton zBtn;
	MCGuiButton dotBtn;
	MCGuiButton idotBtn;
	MCGuiButton edotBtn;
	MCGuiButton minusBtn;
	MCGuiButton plusBtn;
	MCGuiButton equalBtn;
	MCGuiButton underscoreBtn;
	MCGuiButton asterixBtn;
	MCGuiButton zeroBtn;
	MCGuiButton oneBtn;
	MCGuiButton twoBtn;
	MCGuiButton threeBtn;
	MCGuiButton fourBtn;
	MCGuiButton fiveBtn;
	MCGuiButton sixBtn;
	MCGuiButton sevenBtn;
	MCGuiButton eightBtn;
	MCGuiButton nineBtn;

	public ForgeEventHandler(TouchControlsMod mod) {
		homeMod = mod;
		overlay = new GuiTouchOverlay(Minecraft.getMinecraft());
		overlay.initGui();

		escapeBtn = new MCGuiButton(0, "Esc", 0, 0, 50, 20);
		escapeBtn.registerButtonPushListener(this);

		sendBtn = new MCGuiButton(0, "Enter", 0, 20, 50, 20);
		sendBtn.registerButtonPushListener(this);

		slashBtn = new MCGuiButton(0, "/", 0, 40, 50, 20);
		slashBtn.registerButtonPushListener(this);

		backBtn = new MCGuiButton(0, "Back", 0, 60, 50, 20);
		backBtn.registerButtonPushListener(this);

		spaceBtn = new MCGuiButton(0, "Space", 0, 80, 50, 20);
		spaceBtn.registerButtonPushListener(this);

		tabBtn = new MCGuiButton(0, "Tab", 0, 100, 50, 20);
		tabBtn.registerButtonPushListener(this);

		capsBtn = new MCGuiButton(0, "Caps", 0, 120, 50, 20);
		capsBtn.registerButtonPushListener(this);

		aBtn = new MCGuiButton(0, "A", 50, 0, 20, 20);
		aBtn.registerButtonPushListener(this);

		bBtn = new MCGuiButton(0, "B", 70, 0, 20, 20);
		bBtn.registerButtonPushListener(this);

		cBtn = new MCGuiButton(0, "C", 90, 0, 20, 20);
		cBtn.registerButtonPushListener(this);

		dBtn = new MCGuiButton(0, "D", 110, 0, 20, 20);
		dBtn.registerButtonPushListener(this);

		eBtn = new MCGuiButton(0, "E", 130, 0, 20, 20);
		eBtn.registerButtonPushListener(this);

		fBtn = new MCGuiButton(0, "F", 150, 0, 20, 20);
		fBtn.registerButtonPushListener(this);

		gBtn = new MCGuiButton(0, "G", 170, 0, 20, 20);
		gBtn.registerButtonPushListener(this);

		hBtn = new MCGuiButton(0, "H", 190, 0, 20, 20);
		hBtn.registerButtonPushListener(this);

		iBtn = new MCGuiButton(0, "I", 210, 0, 20, 20);
		iBtn.registerButtonPushListener(this);
		// nextline
		jBtn = new MCGuiButton(0, "J", 50, 20, 20, 20);
		jBtn.registerButtonPushListener(this);

		kBtn = new MCGuiButton(0, "K", 70, 20, 20, 20);
		kBtn.registerButtonPushListener(this);

		lBtn = new MCGuiButton(0, "L", 90, 20, 20, 20);
		lBtn.registerButtonPushListener(this);

		mBtn = new MCGuiButton(0, "M", 110, 20, 20, 20);
		mBtn.registerButtonPushListener(this);

		nBtn = new MCGuiButton(0, "N", 130, 20, 20, 20);
		nBtn.registerButtonPushListener(this);

		oBtn = new MCGuiButton(0, "O", 150, 20, 20, 20);
		oBtn.registerButtonPushListener(this);

		pBtn = new MCGuiButton(0, "P", 170, 20, 20, 20);
		pBtn.registerButtonPushListener(this);

		qBtn = new MCGuiButton(0, "Q", 190, 20, 20, 20);
		qBtn.registerButtonPushListener(this);

		rBtn = new MCGuiButton(0, "R", 210, 20, 20, 20);
		rBtn.registerButtonPushListener(this);
		// nextline
		sBtn = new MCGuiButton(0, "S", 50, 40, 20, 20);
		sBtn.registerButtonPushListener(this);

		tBtn = new MCGuiButton(0, "T", 70, 40, 20, 20);
		tBtn.registerButtonPushListener(this);

		uBtn = new MCGuiButton(0, "U", 90, 40, 20, 20);
		uBtn.registerButtonPushListener(this);

		vBtn = new MCGuiButton(0, "V", 110, 40, 20, 20);
		vBtn.registerButtonPushListener(this);

		wBtn = new MCGuiButton(0, "W", 130, 40, 20, 20);
		wBtn.registerButtonPushListener(this);

		xBtn = new MCGuiButton(0, "X", 150, 40, 20, 20);
		xBtn.registerButtonPushListener(this);

		yBtn = new MCGuiButton(0, "Y", 170, 40, 20, 20);
		yBtn.registerButtonPushListener(this);

		zBtn = new MCGuiButton(0, "Z", 190, 40, 20, 20);
		zBtn.registerButtonPushListener(this);
		// nextline
		dotBtn = new MCGuiButton(0, ".", 50, 60, 20, 20);
		dotBtn.registerButtonPushListener(this);

		idotBtn = new MCGuiButton(0, "?", 70, 60, 20, 20);
		idotBtn.registerButtonPushListener(this);

		edotBtn = new MCGuiButton(0, "!", 90, 60, 20, 20);
		edotBtn.registerButtonPushListener(this);

		minusBtn = new MCGuiButton(0, "-", 110, 60, 20, 20);
		minusBtn.registerButtonPushListener(this);

		plusBtn = new MCGuiButton(0, "+", 130, 60, 20, 20);
		plusBtn.registerButtonPushListener(this);

		equalBtn = new MCGuiButton(0, "=", 150, 60, 20, 20);
		equalBtn.registerButtonPushListener(this);

		underscoreBtn = new MCGuiButton(0, "_", 170, 60, 20, 20);
		underscoreBtn.registerButtonPushListener(this);

		asterixBtn = new MCGuiButton(0, "*", 190, 60, 20, 20);
		asterixBtn.registerButtonPushListener(this);
		// nextline
		zeroBtn = new MCGuiButton(0, "0", 50, 80, 20, 20);
		zeroBtn.registerButtonPushListener(this);

		oneBtn = new MCGuiButton(0, "1", 70, 80, 20, 20);
		oneBtn.registerButtonPushListener(this);

		twoBtn = new MCGuiButton(0, "2", 90, 80, 20, 20);
		twoBtn.registerButtonPushListener(this);

		threeBtn = new MCGuiButton(0, "3", 110, 80, 20, 20);
		threeBtn.registerButtonPushListener(this);

		fourBtn = new MCGuiButton(0, "4", 130, 80, 20, 20);
		fourBtn.registerButtonPushListener(this);

		fiveBtn = new MCGuiButton(0, "5", 150, 80, 20, 20);
		fiveBtn.registerButtonPushListener(this);

		sixBtn = new MCGuiButton(0, "6", 170, 80, 20, 20);
		sixBtn.registerButtonPushListener(this);

		sevenBtn = new MCGuiButton(0, "7", 190, 80, 20, 20);
		sevenBtn.registerButtonPushListener(this);

		eightBtn = new MCGuiButton(0, "8", 210, 80, 20, 20);
		eightBtn.registerButtonPushListener(this);

		nineBtn = new MCGuiButton(0, "9", 230, 80, 20, 20);
		nineBtn.registerButtonPushListener(this);

	}

	public void cbutton(MCGuiButton b, GuiScreenEvent.DrawScreenEvent.Post event) {
		b.draw(Minecraft.getMinecraft());
		if (b.checkBounds(event.getMouseX(), event.getMouseY())) {
			if (!b.isMouseOver()) {
				b.onMouseOver(new MCGuiMouseEvent(event.getMouseX(), event.getMouseY()));
			}

			if (Mouse.isButtonDown(0)) {

				if (!b.isMouseDown()) {
					b.onMouseDown(new MCGuiMouseEvent(0, event.getMouseX(), event.getMouseY()));
				}

			} else {
				if (b.isMouseDown()) {
					b.onMouseUp(new MCGuiMouseEvent(0, event.getMouseX(), event.getMouseY()));
					b.onMouseClick(new MCGuiMouseEvent(0, event.getMouseX(), event.getMouseY()));
				}
			}

		} else {
			if (b.isMouseOver()) {
				b.onMouseOut(new MCGuiMouseEvent(event.getMouseX(), event.getMouseY()));
			}
		}

		if (!Mouse.isButtonDown(0)) {
			if (b.isMouseDown()) {
				b.onMouseUp(new MCGuiMouseEvent(0, event.getMouseX(), event.getMouseY()));
			}
		}
	}

	@SubscribeEvent
	public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
		if (event.getType() == ElementType.ALL) {

			width = event.getResolution().getScaledWidth();
			height = event.getResolution().getScaledHeight();

			overlay.width = width;
			overlay.height = height;

			Minecraft mc = Minecraft.getMinecraft();
			boolean canDraw = homeMod.getTouchMode() && mc.currentScreen == null;
			if (!oldCanDraw && canDraw) {
				overlay.resumeGui();
				DebugHelper.log(LogLevel.INFO, "Showing touch screen gui.");
			} else if (oldCanDraw && !canDraw) {
				overlay.pauseGui();
				DebugHelper.log(LogLevel.INFO, "Hiding touch screen gui.");
			}
			oldCanDraw = canDraw;

			if (!oldTouchMode && homeMod.getTouchMode())
				DebugHelper.logTouchAndWindowInfo();
			oldTouchMode = homeMod.getTouchMode();

			if (canDraw) {
				try {
					overlay.handleInput();
					Mouse.setGrabbed(false);
				} catch (IOException e) {
					e.printStackTrace();
				}
				overlay.drawScreen(0, 0, event.getPartialTicks());
			}
		}
	}

	@SubscribeEvent
	public void onMouse(MouseEvent event) {
		if (homeMod.getTouchMode()) {
			event.setCanceled(true);
		}
	}

	boolean escapePressed = false;

	@SubscribeEvent
	public void onDrawScreen(GuiScreenEvent.DrawScreenEvent.Post event) {
		if (escapePressed) {
			escapePressed = false;
			try {
				GuiScreenHack.callKeyTyped(Minecraft.getMinecraft().currentScreen, '\0', 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (homeMod.getTouchMode()) {
			escapeBtn.draw(Minecraft.getMinecraft());
			if (escapeBtn.checkBounds(event.getMouseX(), event.getMouseY())) {
				if (!escapeBtn.isMouseOver()) {
					escapeBtn.onMouseOver(new MCGuiMouseEvent(event.getMouseX(), event.getMouseY()));
				}

				if (Mouse.isButtonDown(0)) {

					if (!escapeBtn.isMouseDown()) {
						escapeBtn.onMouseDown(new MCGuiMouseEvent(0, event.getMouseX(), event.getMouseY()));
					}

				} else {
					if (escapeBtn.isMouseDown()) {
						escapeBtn.onMouseUp(new MCGuiMouseEvent(0, event.getMouseX(), event.getMouseY()));
						escapeBtn.onMouseClick(new MCGuiMouseEvent(0, event.getMouseX(), event.getMouseY()));
					}
				}

			} else {
				if (escapeBtn.isMouseOver()) {
					escapeBtn.onMouseOut(new MCGuiMouseEvent(event.getMouseX(), event.getMouseY()));
				}
			}

			if (!Mouse.isButtonDown(0)) {
				if (escapeBtn.isMouseDown()) {
					escapeBtn.onMouseUp(new MCGuiMouseEvent(0, event.getMouseX(), event.getMouseY()));
				}
			}
		}
		if (homeMod.getTouchMode() && Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen()) {
			cbutton(sendBtn, event);
			cbutton(slashBtn, event);
			cbutton(backBtn, event);
			cbutton(spaceBtn, event);
			cbutton(tabBtn, event);
			cbutton(aBtn, event);
			cbutton(bBtn, event);
			cbutton(cBtn, event);
			cbutton(dBtn, event);
			cbutton(eBtn, event);
			cbutton(fBtn, event);
			cbutton(gBtn, event);
			cbutton(hBtn, event);
			cbutton(iBtn, event);
			cbutton(jBtn, event);
			cbutton(kBtn, event);
			cbutton(lBtn, event);
			cbutton(mBtn, event);
			cbutton(nBtn, event);
			cbutton(oBtn, event);
			cbutton(pBtn, event);
			cbutton(qBtn, event);
			cbutton(rBtn, event);
			cbutton(sBtn, event);
			cbutton(tBtn, event);
			cbutton(uBtn, event);
			cbutton(vBtn, event);
			cbutton(wBtn, event);
			cbutton(xBtn, event);
			cbutton(yBtn, event);
			cbutton(zBtn, event);
			cbutton(dotBtn, event);
			cbutton(idotBtn, event);
			cbutton(edotBtn, event);
			cbutton(minusBtn, event);
			cbutton(plusBtn, event);
			cbutton(equalBtn, event);
			cbutton(underscoreBtn, event);
			cbutton(asterixBtn, event);
			cbutton(zeroBtn, event);
			cbutton(oneBtn, event);
			cbutton(twoBtn, event);
			cbutton(threeBtn, event);
			cbutton(fourBtn, event);
			cbutton(fiveBtn, event);
			cbutton(sixBtn, event);
			cbutton(sevenBtn, event);
			cbutton(eightBtn, event);
			cbutton(nineBtn, event);
			cbutton(capsBtn, event);
		}
	}

	@Override
	public void onButtonPush(MCGuiButtonPushEvent event) {
		System.out.println("onButtonPush");
		if (event.getButton() == escapeBtn) {
			escapePressed = true;
		}

		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (event.getButton() == sendBtn) {
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		}

		if (event.getButton() == capsBtn) {
			robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
		}

		if (event.getButton() == dotBtn) {
			robot.keyPress(KeyEvent.VK_DECIMAL);
			robot.keyRelease(KeyEvent.VK_DECIMAL);
		}

		if (event.getButton() == edotBtn) {
			robot.keyPress(KeyEvent.VK_EXCLAMATION_MARK);
			robot.keyRelease(KeyEvent.VK_EXCLAMATION_MARK);
		}
		// todo
		if (event.getButton() == idotBtn) {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_COMMA);
			robot.keyRelease(KeyEvent.VK_COMMA);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		}

		if (event.getButton() == minusBtn) {
			robot.keyPress(KeyEvent.VK_MINUS);
			robot.keyRelease(KeyEvent.VK_MINUS);
		}

		if (event.getButton() == plusBtn) {
			robot.keyPress(KeyEvent.VK_ADD);
			robot.keyRelease(KeyEvent.VK_ADD);
		}

		if (event.getButton() == equalBtn) {
			robot.keyPress(KeyEvent.VK_PLUS);
			robot.keyRelease(KeyEvent.VK_PLUS);
		}

		if (event.getButton() == underscoreBtn) {
			robot.keyPress(KeyEvent.VK_UNDERSCORE);
			robot.keyRelease(KeyEvent.VK_UNDERSCORE);
		}

		if (event.getButton() == asterixBtn) {
			robot.keyPress(KeyEvent.VK_ASTERISK);
			robot.keyRelease(KeyEvent.VK_ASTERISK);
		}

		if (event.getButton() == slashBtn) {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_SLASH);
			robot.keyRelease(KeyEvent.VK_SLASH);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		}

		if (event.getButton() == backBtn) {
			robot.keyPress(KeyEvent.VK_BACK_SPACE);
			robot.keyRelease(KeyEvent.VK_BACK_SPACE);
		}

		if (event.getButton() == spaceBtn) {
			robot.keyPress(KeyEvent.VK_SPACE);
			robot.keyRelease(KeyEvent.VK_SPACE);
		}

		if (event.getButton() == tabBtn) {
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
		}

		if (event.getButton() == aBtn) {
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
		}
		if (event.getButton() == bBtn) {
			robot.keyPress(KeyEvent.VK_B);
			robot.keyRelease(KeyEvent.VK_B);
		}
		if (event.getButton() == cBtn) {
			robot.keyPress(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_C);
		}
		if (event.getButton() == dBtn) {
			robot.keyPress(KeyEvent.VK_D);
			robot.keyRelease(KeyEvent.VK_D);
		}
		if (event.getButton() == eBtn) {
			robot.keyPress(KeyEvent.VK_E);
			robot.keyRelease(KeyEvent.VK_E);
		}
		if (event.getButton() == fBtn) {
			robot.keyPress(KeyEvent.VK_F);
			robot.keyRelease(KeyEvent.VK_F);
		}
		if (event.getButton() == gBtn) {
			robot.keyPress(KeyEvent.VK_G);
			robot.keyRelease(KeyEvent.VK_G);
		}
		if (event.getButton() == hBtn) {
			robot.keyPress(KeyEvent.VK_H);
			robot.keyRelease(KeyEvent.VK_H);
		}
		if (event.getButton() == iBtn) {
			robot.keyPress(KeyEvent.VK_I);
			robot.keyRelease(KeyEvent.VK_I);
		}
		if (event.getButton() == jBtn) {
			robot.keyPress(KeyEvent.VK_J);
			robot.keyRelease(KeyEvent.VK_J);
		}
		if (event.getButton() == kBtn) {
			robot.keyPress(KeyEvent.VK_K);
			robot.keyRelease(KeyEvent.VK_K);
		}
		if (event.getButton() == lBtn) {
			robot.keyPress(KeyEvent.VK_L);
			robot.keyRelease(KeyEvent.VK_L);
		}
		if (event.getButton() == mBtn) {
			robot.keyPress(KeyEvent.VK_M);
			robot.keyRelease(KeyEvent.VK_M);
		}
		if (event.getButton() == nBtn) {
			robot.keyPress(KeyEvent.VK_N);
			robot.keyRelease(KeyEvent.VK_N);
		}
		if (event.getButton() == oBtn) {
			robot.keyPress(KeyEvent.VK_O);
			robot.keyRelease(KeyEvent.VK_O);
		}
		if (event.getButton() == pBtn) {
			robot.keyPress(KeyEvent.VK_P);
			robot.keyRelease(KeyEvent.VK_P);
		}
		if (event.getButton() == qBtn) {
			robot.keyPress(KeyEvent.VK_Q);
			robot.keyRelease(KeyEvent.VK_Q);
		}
		if (event.getButton() == rBtn) {
			robot.keyPress(KeyEvent.VK_R);
			robot.keyRelease(KeyEvent.VK_R);
		}
		if (event.getButton() == sBtn) {
			robot.keyPress(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_S);
		}
		if (event.getButton() == tBtn) {
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_T);
		}
		if (event.getButton() == uBtn) {
			robot.keyPress(KeyEvent.VK_U);
			robot.keyRelease(KeyEvent.VK_U);
		}
		if (event.getButton() == vBtn) {
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
		}
		if (event.getButton() == wBtn) {
			robot.keyPress(KeyEvent.VK_W);
			robot.keyRelease(KeyEvent.VK_W);
		}
		if (event.getButton() == xBtn) {
			robot.keyPress(KeyEvent.VK_X);
			robot.keyRelease(KeyEvent.VK_X);
		}
		if (event.getButton() == yBtn) {
			robot.keyPress(KeyEvent.VK_Y);
			robot.keyRelease(KeyEvent.VK_Y);
		}
		if (event.getButton() == zBtn) {
			robot.keyPress(KeyEvent.VK_Z);
			robot.keyRelease(KeyEvent.VK_Z);
		}
		if (event.getButton() == zeroBtn) {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_0);
			robot.keyRelease(KeyEvent.VK_0);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		}
		if (event.getButton() == oneBtn) {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_1);
			robot.keyRelease(KeyEvent.VK_1);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		}
		if (event.getButton() == twoBtn) {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_2);
			robot.keyRelease(KeyEvent.VK_2);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		}
		if (event.getButton() == threeBtn) {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_3);
			robot.keyRelease(KeyEvent.VK_3);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		}
		if (event.getButton() == fourBtn) {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_4);
			robot.keyRelease(KeyEvent.VK_4);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		}
		if (event.getButton() == fiveBtn) {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_5);
			robot.keyRelease(KeyEvent.VK_5);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		}
		if (event.getButton() == sixBtn) {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_6);
			robot.keyRelease(KeyEvent.VK_6);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		}
		if (event.getButton() == sevenBtn) {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_7);
			robot.keyRelease(KeyEvent.VK_7);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		}
		if (event.getButton() == eightBtn) {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_8);
			robot.keyRelease(KeyEvent.VK_8);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		}
		if (event.getButton() == nineBtn) {
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_9);
			robot.keyRelease(KeyEvent.VK_9);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		}
	}
}