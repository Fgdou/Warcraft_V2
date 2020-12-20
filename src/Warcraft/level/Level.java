package Warcraft.level;

import Warcraft.tools.InputHandler;
import Warcraft.entities.Entity;
import Warcraft.entities.monsters.Monster;
import Warcraft.entities.projectiles.Projectile;
import Warcraft.entities.towers.Tower;
import Warcraft.entities.towers.TowerArcher1;
import Warcraft.entities.towers.TowerBomb1;
import Warcraft.screen.Screen;
import Warcraft.screen.textures.Texture;
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

		for(int i=0; i<tickSpeed; i++){
			updateEntities();
			updateKeyboard();
			updateState();

			if(waves.hasNext()){
				if(!hasMob())
					waves.startWave();

				Monster m = waves.getNext();

				if(m != null)
					addEntity(m);
			}

			if(lives <= 0){
				lives = 0;
				stop();
			}
		}
	}

	private void updateState(){
		Vec2i mouse = inputHandler.getMouseTile();
		if(state == State.NewTower){
			newTower.setPos(new Vec2(mouse));
		}
		if(inputHandler.getMouseClicked()){
			if(state == State.NewTower && coins >= newTower.getCost() && tiles[mouse.y][mouse.x] == Tiles.EMPTY) {
				addEntity(newTower);
				newTower = newTower.copy();
				coins -= newTower.getCost();
				tiles[mouse.y][mouse.x] = Tiles.TOWER;
			}else if(state == State.UpgradeTower){
				Tower founded = null;
				for(Tower t : towers){
					if(t.costUpgrade() <= coins && t.isUpgradable() && t.getPos().equals(new Vec2(mouse))){
						founded = t;
					}
				}

				if(founded != null){
					coins -= founded.costUpgrade();
					addEntity(founded.getUpgrade());
					towers.remove(founded);
				}
			}
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
			newTower = new TowerBomb1(new Vec2(inputHandler.getMouseTile()));
		}else if(c == ' '){
			tickSpeed += 5;
		}else{
			state = State.Normal;
			setSpeed(1);
		}

		if(c == 'q')
			stop();
	}
	public void updateEntities(){
		Iterator<Tower> it = towers.iterator();
		while(it.hasNext()){
			Tower t = it.next();
			t.onTick(this);
			if(!t.isAlive()) {
				it.remove();
				tiles[(int)t.getPos().y][(int)t.getPos().x] = Tiles.EMPTY;
			}
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
			Monster cloth = null;
			double dist = 0;
			for(Monster m : monsters) {
				if(cloth == null || m.getPos().distance(t.getPos()) < dist){
					dist = m.getPos().distance(t.getPos());
					cloth = m;
				}
			}
			if(cloth == null)
				continue;
			t.onInteract(cloth, this);
		}
		for(Monster m : monsters){
			Tower cloth = null;
			double dist = 0;
			for(Tower t : towers) {
				if(cloth == null || t.getPos().distance(m.getPos()) < dist){
					dist = t.getPos().distance(m.getPos());
					cloth = t;
				}
			}
			if(cloth == null)
				continue;
			m.onInteract(cloth, this);
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
		if(e == null)
			return;
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

		screen.drawProgressBar((new Vec2(spawn)).add(new Vec2(0, -.5)), new Vec2(.5, .1), waves.getPercent(), Color.BLUE);
	}
	private void drawState(){
		if(state == State.NewTower && newTower != null){
			newTower.onDraw(screen);
			Color c = Color.GREEN;
			if(newTower.getCost() > coins || tiles[(int)newTower.getPos().y][(int)newTower.getPos().x] != Tiles.EMPTY)
				c = Color.RED;

			screen.drawRectangle(newTower.getPos(), new Vec2(.45, .45), c);
			screen.drawCircle(newTower.getPos(), newTower.getAttack().getRange(), Color.BLACK);
			screen.drawTextImageRight(newTower.getPos().add(new Vec2(.7, .5)), 20, Color.BLACK, String.valueOf(newTower.getCost()), "assets/images/coin.png");
		}
		for(Tower t : towers){
			Color c = Color.GRAY;

			if(state == State.UpgradeTower && t.isUpgradable()){
				if(t.costUpgrade() > coins)
					c = Color.RED;
				else if(t.getPos().equals(new Vec2(inputHandler.getMouseTile()))){
					c = Color.orange;
				}else
					c = Color.GREEN;
				screen.drawRectangle(t.getPos(), new Vec2(.45, .45), c);
				if(t.isUpgradable()){
					screen.drawTextImageRight(t.getPos().add(new Vec2(.7, .5)), 20, c, String.valueOf(t.costUpgrade()), "assets/images/coin.png");
				}
			}
			if((state == State.UpgradeTower || state == State.Normal) && t.getPos().equals(new Vec2(inputHandler.getMouseTile()))){
				screen.drawCircle(t.getPos(), t.getAttack().getRange(), Color.BLACK);
			}
		}
	}
	private void drawInfos(){
		screen.drawTextImageRightAbsolute(new Vec2(1, 0.96), 30, Color.BLACK, String.valueOf(coins), "assets/images/coin.png");
		screen.drawTextImageRightAbsolute(new Vec2(1, 0.92), 30, Color.BLACK, String.valueOf(lives), "assets/images/heart.png");
	}

	public void hurt(int damage){
		lives -= damage;
	}

	public boolean hasMob(){
		return (monsters.size() != 0);
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
	public void addCoins(int coins) {
		this.coins += coins;
	}
}
