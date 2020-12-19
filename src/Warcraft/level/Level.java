package Warcraft.level;

import Warcraft.entities.monsters.Monster;
import Warcraft.entities.projectiles.Projectile;
import Warcraft.entities.towers.Tower;
import Warcraft.fx.Screen;

import java.util.LinkedList;
import java.util.List;

public class Level {
	private Path path;
	private List<Wave> waves;
	private Screen screen;

	private int[][] tiles;
	private List<Monster> monsters;
	private List<Tower> towers;
	private List<Projectile> projectiles;

	private boolean tickEnable;
	private int tickSpeed;
	private boolean ended;
	
	public Level(Screen screen, Path path, List<Wave> waves){
		this.screen = screen;
		this.path = path;
		this.waves = waves;
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

	}
}
