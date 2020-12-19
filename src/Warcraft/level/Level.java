package Warcraft.level;

import Warcraft.InputHandler;
import Warcraft.entities.Entity;
import Warcraft.entities.monsters.Monster;
import Warcraft.entities.projectiles.Projectile;
import Warcraft.entities.towers.Tower;
import Warcraft.fx.Screen;
import Warcraft.fx.textures.Texture;
import Warcraft.fx.textures.TextureImage;
import Warcraft.tools.Vec2;
import Warcraft.tools.Vec2i;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Level {
	public enum Tiles{
		EMPTY,
		PATH,
		TOWER,
		SPAWN,
		CASTLE
	}

	private PathRandom path;
	private List<Wave> waves;
	private Screen screen;
	private InputHandler inputHandler;

	private Tiles[][] tiles;
	private Vec2i spawn;
	private Vec2i castle;

	private List<Monster> monsters;
	private List<Tower> towers;
	private List<Projectile> projectiles;

	private boolean tickEnable;
	private int tickSpeed;
	private boolean ended;
	
	public Level(Screen screen, InputHandler inputHandler, PathRandom path, List<Wave> waves){
		this.screen = screen;
		this.path = path;
		this.waves = waves;
		this.inputHandler = inputHandler;

		tiles = new Tiles[screen.getnTiles().y][screen.getnTiles().x];
		monsters = new LinkedList<>();
		towers = new LinkedList<>();
		projectiles = new LinkedList<>();

		tickEnable = false;
		ended = false;
		tickSpeed = 1;

		fillTilesWithPath();
	}

	private void fillTilesWithPath() {
		//Clearing the table
		for (Tiles[] tile : tiles) {
			Arrays.fill(tile, Tiles.EMPTY);
		}

		//Set the spawn with first path
		spawn = new Vec2i(path.get(0));
		tiles[spawn.y][spawn.x] = Tiles.SPAWN;

		//Fill with the path
		for(int i=1; i<path.length()-1; i++){
			Vec2i pos = new Vec2i(path.get(i));
			tiles[pos.y][pos.x] = Tiles.PATH;
		}

		//Set the castle to the last path
		castle = new Vec2i(path.get(path.length()-1));
		tiles[castle.y][castle.x] = Tiles.CASTLE;
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

		Iterator<Tower> it = towers.iterator();
		while(it.hasNext()){
			Tower t = it.next();
			t.onTick(this);
			if(!t.isAlive())
				it.remove();
		}

		Iterator<Monster> im = monsters.iterator();
		while(im.hasNext()){
			Monster t = im.next();
			t.onTick(this);
			if(!t.isAlive())
				im.remove();
		}

		Iterator<Projectile> ip = projectiles.iterator();
		while(ip.hasNext()){
			Projectile t = ip.next();
			t.onTick(this);
			if(!t.isAlive())
				ip.remove();
		}

		for(Tower t : towers){
			for(Monster m : monsters) {
				m.onInteract(t);
				t.onInteract(m);
			}
		}
	}
	public void draw(){
		drawBackground();

		for(Tower t : towers)
			t.onDraw(screen);
		for(Monster m : monsters)
			m.onDraw(screen);
		for(Projectile p : projectiles)
			p.onDraw(screen);
	}

	public void addEntity(Entity e){
		if(e instanceof Monster)
			monsters.add((Monster)e);
		else if(e instanceof Tower)
			towers.add((Tower)e);
		else if(e instanceof Projectile)
			projectiles.add((Projectile)e);
		else
			throw new RuntimeException("e not found");
	}
	public void drawBackground(){
		for(int i=0; i<screen.getnTiles().y; i++){
			for(int j=0; j<screen.getnTiles().x; j++){
				Texture t;

				Tiles tile = tiles[i][j];

				if(tile == Tiles.PATH)
					t = Texture.PATH;
				else
					t = Texture.BACKGROUND;

				t.draw(screen, new Vec2(j, i), 1, 0);
			}
		}
		Texture.CASTLE.draw(screen, new Vec2(castle), 1, 0);
		Texture.SPAWN.draw(screen, new Vec2(spawn), 1, 0);
	}


	public PathRandom getPath() {
		return path;
	}
	public List<Wave> getWaves() {
		return waves;
	}
	public Screen getScreen() {
		return screen;
	}
	public InputHandler getInputHandler() {
		return inputHandler;
	}
	public Vec2i getSpawn() {
		return spawn;
	}
	public Vec2i getCastle() {
		return castle;
	}
}
