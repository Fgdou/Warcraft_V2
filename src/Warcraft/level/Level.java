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
	private int gameSpeed;
	private boolean ended;

	private State selectedState;
	private Tower stateNewTower;

	private int coins;
	private int lives;

	/**
	 * @param screen			the screen to render to
	 * @param inputHandler		the inputs for the game
	 * @param path				the path already filled
	 * @param waves				the waves for the game
	 */
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
		gameSpeed = 1;
		selectedState = State.Normal;
		stateNewTower = null;
		lives = 20;
		coins = 100;

		fillTilesWithPath();
	}

	/**
	 * Fill the array with the path
	 */
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

	/**
	 * @param pos 	Position on the array of a tile
	 * @return		If this tile is free to place a tower
	 */
	private boolean isTileFree(Vec2i pos){
		if(pos.x < 0 || pos.y < 0 || pos.y >= tiles.length || pos.x >= tiles[0].length)
			return false;
		return tiles[pos.y][pos.x] == Tiles.EMPTY;
	}

	/**
	 * @return if the game is finished
	 */
	public boolean ended(){
		return ended;
	}

	/**
	 * Stop the game
	 */
	public void stop(){
		ended = true;
		tickEnable = false;
	}
	/**
	 * Start the game
	 */
	public void start(){
		tickEnable = true;
	}
	/**
	 * Pause the game
	 */
	public void pause(){
		tickEnable = false;
	}

	/**
	 * Set the speed of the game. 1 is 60 ticks per sec
	 * @param factor  1 default and more
	 */
	public void setSpeed(int factor){
		gameSpeed = factor;
	}

	/**
	 * Calc all the positions and animamtions
	 */
	public void tick() {

		//Check is the game run
		if(!tickEnable) {
			updateKeyboard();
			return;
		}

		//Execute the ticks with the speed value (1 is default)
		for(int i = 0; i< gameSpeed; i++){
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

	/**
	 * Logic for the placement and the upgrade
	 */
	private void updateState(){

		//Position of the mouse
		Vec2i mouse = inputHandler.getMouseTile();
		if(selectedState == State.NewTower){
			stateNewTower.setPos(new Vec2(mouse));
		}

		//If mouse click
		if(inputHandler.getMouseClicked()){

			if(selectedState == State.NewTower && coins >= stateNewTower.getCost() && isTileFree(mouse)) {
				//Place the tower
				addEntity(stateNewTower);
				stateNewTower = stateNewTower.copy();
				coins -= stateNewTower.getCost();
				tiles[mouse.y][mouse.x] = Tiles.TOWER;
			}else if(selectedState == State.UpgradeTower){
				//Upgrade the tower
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

	/**
	 * Logic for the keyboard
	 */
	private void updateKeyboard() {
		if(!inputHandler.hastNextKey())
			return;

		char c = inputHandler.getNextKey();
		if(c == 'e')
			selectedState = State.UpgradeTower;
		else if(c == 'a') {
			selectedState = State.NewTower;
			stateNewTower = new TowerArcher1(new Vec2(inputHandler.getMouseTile()));
		}else if(c == 'b'){
			selectedState = State.NewTower;
			stateNewTower = new TowerBomb1(new Vec2(inputHandler.getMouseTile()));
		}else if(c == ' '){
			gameSpeed += 5;
		}else if(c == 's'){
			start();
		}else{
			selectedState = State.Normal;
			setSpeed(1);
		}

		if(c == 'q')
			stop();
	}

	/**
	 * Logic for all the entities
	 */
	public void updateEntities(){

		//Towers
		Iterator<Tower> it = towers.iterator();
		while(it.hasNext()){
			Tower t = it.next();
			t.onTick(this);
			if(!t.isAlive()) {
				it.remove();
				tiles[(int)t.getPos().y][(int)t.getPos().x] = Tiles.EMPTY;
			}
		}

		//Monsters
		Iterator<Monster> im = monsters.iterator();
		while(im.hasNext()){
			Monster t = im.next();
			t.onTick(this);
			if(!t.isAlive())
				im.remove();
		}

		//Projectiles
		Iterator<Projectile> ip = projectiles.iterator();
		while(ip.hasNext()){
			Projectile t = ip.next();
			t.onTick(this);
			if(!t.isAlive())
				ip.remove();
		}

		//Towers and monsters -> interactions
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

	/**
	 * Draw everything on the screen
	 */
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

	/**
	 * Add an entity to the list of tick and draw
	 * @param e the entity to add
	 */
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

	/**
	 * Draw grass spawn and castle, with the progress bar of the wave
	 */
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

	/**
	 * Draw the mouse when adding or upgrading tower
	 */
	private void drawState(){
		if(selectedState == State.NewTower && stateNewTower != null){
			stateNewTower.onDraw(screen);
			Color c = Color.GREEN;
			if(stateNewTower.getCost() > coins || !isTileFree(new Vec2i(stateNewTower.getPos())))
				c = Color.RED;

			screen.drawRectangle(stateNewTower.getPos(), new Vec2(.45, .45), c);
			screen.drawCircle(stateNewTower.getPos(), stateNewTower.getAttack().getRange(), Color.BLACK);
			screen.drawTextImageRight(stateNewTower.getPos().add(new Vec2(.7, .5)), 20, Color.BLACK, String.valueOf(stateNewTower.getCost()), "assets/images/coin.png");
		}
		for(Tower t : towers){
			Color c = Color.GRAY;

			if(selectedState == State.UpgradeTower && t.isUpgradable()){
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
			if((selectedState == State.UpgradeTower || selectedState == State.Normal) && t.getPos().equals(new Vec2(inputHandler.getMouseTile()))){
				screen.drawCircle(t.getPos(), t.getAttack().getRange(), Color.BLACK);
			}
		}
	}

	/**
	 * Draw life and coins
	 */
	private void drawInfos(){
		screen.drawTextImageRightAbsolute(new Vec2(1, 0.96), 30, Color.BLACK, String.valueOf(coins), "assets/images/coin.png");
		screen.drawTextImageRightAbsolute(new Vec2(1, 0.92), 30, Color.BLACK, String.valueOf(lives), "assets/images/heart.png");
	}

	/**
	 * Remove lives on the game
	 * @param damage number of hurts
	 */
	public void hurt(int damage){
		lives -= damage;
	}

	/**
	 * @return if there is any mob on the tiles
	 */
	public boolean hasMob(){
		return (monsters.size() != 0);
	}

	/**
	 * @return the path of the level
	 */
	public PathRandom getPath() {
		return path;
	}

	/**
	 * @return the waves of the level
	 */
	public Wave getWaves() {
		return waves;
	}

	/**
	 * @return the screen of the level
	 */
	public Screen getScreen() {
		return screen;
	}

	/**
	 * @return the input handler of the level
	 */
	public InputHandler getInputHandler() {
		return inputHandler;
	}

	/**
	 * @return the position of the spawn
	 */
	public Vec2i getSpawn() {
		return spawn;
	}

	/**
	 * @return the position of the castle
	 */
	public Vec2i getCastle() {
		return castle;
	}

	/**
	 * Add coins to the balance
	 * @param coins  coins to add
	 */
	public void addCoins(int coins) {
		this.coins += coins;
	}
}
