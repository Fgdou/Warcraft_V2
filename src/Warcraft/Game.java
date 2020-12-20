package Warcraft;

import Warcraft.screen.Screen;
import Warcraft.level.Level;
import Warcraft.level.PathRandom;
import Warcraft.level.Wave;
import Warcraft.tools.Vec2;
import Warcraft.tools.Vec2i;

import java.awt.*;
import java.io.FileNotFoundException;

public class Game {

	private Screen screen;
	private Level level;
	private int TICK_RATE = 60;

	private InputHandler inputHandler;

	private boolean running;

	public Game() {
		screen = new Screen(new Vec2i(1000, 800), 10);
		inputHandler = new InputHandler(screen);
		level = new Level(screen, inputHandler, new PathRandom(screen.getnTiles(), new Vec2i(1,1)), new Wave("assets/levels/level1.txt"));
		running = true;

	}

	public void tick(){
		inputHandler.tick();
		level.tick();
	}
	public void draw(){
		level.draw();
	}

	public void run(){
		long time = System.nanoTime();
		long lastTime = time;
		long lastTimeRate = time;

		int countFps = 0;
		int countTps = 0;
		int lastCountFps = 0;
		int lastCountTps = 0;

		final int delta = 1000000000 / TICK_RATE;

		while(running){
			time = System.nanoTime();

			while(time - lastTime > delta){
				lastTime += delta;
				countTps++;
				tick();
			}
			countFps++;
			screen.clear();
			draw();

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

	public static void main(String[] args) throws FileNotFoundException {
		Game g = new Game();
		g.run();
	}
}
