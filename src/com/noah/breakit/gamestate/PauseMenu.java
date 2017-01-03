package com.noah.breakit.gamestate;

import com.noah.breakit.component.Label;
import com.noah.breakit.component.Panel;
import com.noah.breakit.component.PushButton;
import com.noah.breakit.game.Game;
import com.noah.breakit.graphics.Screen;
import com.noah.breakit.input.Keyboard;
import com.noah.breakit.sound.SoundFX;
import com.noah.breakit.sound.music.Jukebox;
import com.noah.breakit.transition.PixelDrip;

public class PauseMenu extends GameState {
	
	private Keyboard key = null;
	
	private Panel panel = null;
	
	private int w = 120;
	private int h = 90;
	
	public PauseMenu(Keyboard key, GameState parentGameState) {
		this.key = key;
		this.pixels = parentGameState.pixels;
		this.pgs = parentGameState;
		
		int x = Game.WIDTH / 2 - w / 2;
		int y = Game.HEIGHT / 2 - h / 2;
		
		int x1 = x + w / 2 - ("pause".length() * 8) / 2;
		int y1 = y + 8;
		
		int x2 = x + w / 2 - ("music".length() * 8) / 2;
		int y2 = y1 + 20;
		
		int x3 = x + w / 2 - ("quit to title".length() * 8) / 2;
		int y3 = y2 + 16;
		
		int x4 = x + w / 2 - ("exit program".length() * 8) / 2;
		int y4 = y3 + 16;
		
		panel = new Panel(x, y, w, h, key, 
						  new Label(x1, y1, "pause"),
						  new PushButton(x2, y2, new Label(x2, y2, "music"), () -> musicMenu()),
						  new PushButton(x3, y3, new Label(x3, y3, "quit to title"), () -> quitToTitle()),
						  new PushButton(x4, y4, new Label(x4, y4, "exit program"), () -> exit())
						  );
	}
	
	public void init() {
		panel.setGameState(this);
	}
	
	public void updateGS() {
		
		Jukebox.play(pgs.currSong, true);
		
		key.update();
		if(key.esc && !key.escLast) {
			SoundFX.MENU_3.play();
			finished = true;
			Jukebox.setVolume(Jukebox.DEFAULT_VOLUME);
		}
		panel.update();
	}

	public void renderGS(Screen screen) {
		renderScreenCap(screen);
		panel.render(screen);
	}

	public void updateTX() {
		
	}

	public void renderTX(Screen screen) {
		
	}
	
	private void musicMenu() {
		captureScreen();
		Game.GSM.push(new MusicMenu(key, this));
	}
	
	private void quitToTitle() {
		Jukebox.setVolume(Jukebox.DEFAULT_VOLUME);
		pgs.setNextGameState(new TitleScreen(key));
		pgs.setTransitioning(true, new PixelDrip(0xff00ff));
		finished = true;
	}
	
	private void exit() {
		System.exit(0);
	}
	
	protected void loadNextGameState() {
		//leave blank
	}
}