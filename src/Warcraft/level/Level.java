package Warcraft.level;

import Warcraft.entities.monsters.Monster;
import Warcraft.entities.projectiles.Projectile;
import Warcraft.entities.towers.Tower;
import Warcraft.fx.Screen;
import Warcraft.tools.Vec2;

import java.util.List;

public class Level {
	private Path path;
	private List<Wave> waves;
	private Screen screen;
	
	private int[][] tiles;
	private List<Monster> monsters;
	private List<Tower> towers;
	private List<Projectile> projectiles;
	
	public Level(Screen screen){
		this.screen = screen;
	}
}
