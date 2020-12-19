package Warcraft.level;

import Warcraft.InputHandler;
import Warcraft.entities.Entity;
import Warcraft.entities.monsters.Monster;
import Warcraft.entities.projectiles.Projectile;
import Warcraft.entities.towers.Tower;
import Warcraft.entities.towers.TowerArcher1;
import Warcraft.fx.Screen;
import Warcraft.fx.textures.Texture;
import Warcraft.tools.Vec2;
import Warcraft.tools.Vec2i;

import java.awt.*;
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
	public enum State{
		Normal,
		NewTower,
		UpgradeTower
	}

	private PathRandom path;
	private Wave waves;
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

	private State state;
	private Tower newTower;

	private int coins;
	private int lives;
	
	public Level(Screen screen, InputHandler inputHandler, PathRandom path, Wave waves){
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
		state = State.Normal;
		newTower = null;
		lives = 20;
		coins = 100;

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
	private boolean isTileFree(Vec2i pos){
		return tiles[pos.y][pos.x] == Tiles.EMPTY;
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

		updateEntities();
		updateKeyboard();
		updateState();

		if(waves.hasNext()){
			Monster m = waves.getNext();

			if(m != null)
				addEntity(m);
		}
	}

	private void updateState(){
		if(state == State.NewTower){
			newTower.setPos(new Vec2(inputHandler.getMouseTile()));
		}
		if(inputHandler.getMouseClicked() && state == State.NewTower && coins >= newTower.getCost()){
			addEntity(newTower);
			newTower = newTower.copy();
			coins -= newTower.getCost();
		}
	}
	private void updateKeyboard() {
		if(!inputHandler.hastNextKey())
			return;

		char c = inputHandler.getNextKey();
		if(c == 'e')
			state = State.UpgradeTower;
		else if(c == 'a') {
			state = State.NewTower;
			newTower = new TowerArcher1(new Vec2(inputHandler.getMouseTile()));
		}else if(c == 'b'){
			state = State.NewTower;
			//TODO bomb
		}else{
			state = State.Normal;
		}
		if(c == 'q')
			stop();
	}
	public void updateEntities(){
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
				m.onInteract(t, this);
				t.onInteract(m, this);
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

		drawState();
		drawInfos();
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
	private void drawBackground(){
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
	private void drawState(){
		if(state == State.NewTower && newTower != null){
			newTower.onDraw(screen);
			Color c = Color.GREEN;
			if(newTower.getCost() > coins)
				c = Color.RED;

			screen.drawCircle(newTower.getPos(), newTower.getAttack().getRange(), c);
			screen.drawRectangle(newTower.getPos(), new Vec2(.45, .45), c);
		}

		if(state == State.UpgradeTower)
			for(Tower t : towers){
				Color c = Color.GRAY;

				if(t.isUpgradable()){
					if(t.costUpgrade() > coins)
						c = Color.RED;
					else
						c = Color.GREEN;
				}

				screen.drawCircle(t.getPos(), t.getAttack().getRange(), c);
				screen.drawRectangle(t.getPos(), new Vec2(.45, .45), c);
			}
	}
	private void drawInfos(){
		screen.drawTextRightAbsolute(new Vec2(1, 0.98), 20, Color.BLACK, String.valueOf(coins));
		screen.drawTextRightAbsolute(new Vec2(1, 0.95), 20, Color.BLACK, String.valueOf(lives));
	}

	public void hurt(int damage){
		lives -= damage;
	}


	public PathRandom getPath() {
		return path;
	}
	public Wave getWaves() {
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
