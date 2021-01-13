package Warcraft;

import Warcraft.screen.Screen;
import Warcraft.level.Level;
import Warcraft.level.PathRandom;
import Warcraft.level.Wave;
import Warcraft.tools.InputHandler;
import Warcraft.tools.Vec2;
import Warcraft.tools.Vec2i;
import Warcraft.ui.UiGame;

import java.awt.*;

/**
 * This class is the main class
 * It handle the interface between the player and the game (screen, level, tick, draw)
 */

public class Game {

	private final Screen screen;
	private final Level level;
	private final UiGame uiGame;

	private final int TICK_RATE = 60;

	private final InputHandler inputHandler;

	private boolean running;

	/**
	 * Create the game
	 */
	public Game() {
		screen = new Screen(new Vec2i(1000, 800), 100);
		inputHandler = new InputHandler(screen);
		
		PathRandom path = new PathRandom(screen.getnTiles(), new Vec2i(1,1), screen);
		Wave wave = new Wave("assets/levels/level1.txt");
		level = new Level(screen, inputHandler, path, wave);
		
		running = true;
		uiGame = new UiGame(level, screen);
	}

	/**
	 * Logic of the game
	 */
	private void tick(){
		inputHandler.tick();
		uiGame.update();
		level.tick();
	}

	/**
	 * Drawing of the game
	 */
	private void draw(){
		level.draw();
		uiGame.draw();
	}

	/**
	 * Launch the game, but wait for pressing S
	 */
	public void run(){
		long time = System.nanoTime();
		long lastTime = time;
		long lastTimeRate = time;

		int countFps = 0;
		int countTps = 0;
		int lastCountFps = 0;
		int lastCountTps = 0;

		//Time for 1 frame
		final int delta = 1000000000 / TICK_RATE;

		while(running){
			time = System.nanoTime();

			//Do necessary ticks to keep up with the time
			while(time - lastTime > delta){
				lastTime += delta;
				countTps++;
				tick();
			}

			//Then potentially the drawing, because drawing is very slow in StdDraw
			countFps++;
			screen.clear();
			draw();

			//Show the tps and fps
			if(time - lastTimeRate > 1000000000){
				lastTimeRate += 1000000000;

				lastCountFps = countFps;
				lastCountTps = countTps;

				countFps = 0;
				countTps = 0;
			}
			screen.drawTextLeftAbsolute(new Vec2(0, 0.98), 20, Color.black, "Tps: " + lastCountTps);
			screen.drawTextLeftAbsolute(new Vec2(0, 0.95), 20, Color.black, "Fps: " + lastCountFps);

			screen.render();

			if(level.ended())
				running = false;
		}
	}

	public static void main(String[] args) {
		Game g = new Game();
		g.run();
	}
}
