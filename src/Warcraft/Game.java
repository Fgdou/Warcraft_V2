package Warcraft;

import Warcraft.fx.Screen;
import Warcraft.fx.textures.Texture;
import Warcraft.fx.textures.TextureAnimated;
import Warcraft.fx.textures.TextureImage;
import Warcraft.level.Level;
import Warcraft.tools.Vec2;
import Warcraft.tools.Vec2i;
import org.w3c.dom.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Game {

	private Screen screen;
	private Level level;
	private int TICK_RATE = 60;

	private boolean running;

	public Game(){
		screen = new Screen(new Vec2i(1280, 720), 20);
		level = new Level(screen, null, null);
		running = true;
	}

	public void tick(){
		//TODO keyboard and mouse
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
		}
	}

	public static void main(String[] args) {
		Game g = new Game();
		g.run();
	}
}
