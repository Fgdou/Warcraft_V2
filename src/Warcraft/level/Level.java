package Warcraft.level;

import Warcraft.Input;
import Warcraft.entities.monsters.Monster;
import Warcraft.entities.projectiles.Projectile;
import Warcraft.entities.towers.Tower;
import Warcraft.fx.Screen;
import Warcraft.fx.textures.Texture;
import Warcraft.fx.textures.TextureImage;
import Warcraft.tools.Vec2;

import java.util.LinkedList;
import java.util.List;

public class Level {
	private Path path;
	private List<Wave> waves;
	private Screen screen;
	private Input input;

	private int[][] tiles;
	private List<Monster> monsters;
	private List<Tower> towers;
	private List<Projectile> projectiles;

	private boolean tickEnable;
	private int tickSpeed;
	private boolean ended;
	
	public Level(Screen screen, Input input, Path path, List<Wave> waves){
		this.screen = screen;
		this.path = path;
		this.waves = waves;
		this.input = input;

		tiles = new int[screen.getnTiles().y][screen.getnTiles().x];
		monsters = new LinkedList<>();
		towers = new LinkedList<>();
		projectiles = new LinkedList<>();

		tickEnable = false;
		ended = false;
		tickSpeed = 1;
	}

	public boolean ended(){
		return ended;
	}
	public void stop(){
		ended = true;
		tickEnable = false;
	}
	public void start(){
		tickEnable = true;
	}
	public void pause(){
		tickEnable = false;
	}
	public void setSpeed(int factor){
		tickSpeed = factor;
	}

	public void tick() {
		if(tickEnable)
			return;
	}
	public void draw(){
		drawBackground();
	}

	public void drawBackground(){
		Texture t = new TextureImage("assets/images/background.png");
		for(int i=0; i<screen.getnTiles().y; i++){
			for(int j=0; j<screen.getnTiles().x; j++){
				t.draw(screen, new Vec2(j, i), 1, 0);
			}
		}
	}
}
