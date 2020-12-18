package Warcraft;

import Warcraft.fx.Screen;
import Warcraft.tools.Vec2;
import Warcraft.tools.Vec2i;

public class World {
	public static void main(String[] args) throws InterruptedException {
		Screen s = new Screen(new Vec2i(1200, 600), 10);
		s.clear();
		for(int i = 0; i<s.getnTiles().y; i++){
			for(int j = 0; j<s.getnTiles().x; j++){
				Vec2i p = new Vec2i(j, i);
				s.drawImage(p,  new Vec2(1, 1), "assets/images/arrow.png", 0);
			}
		}
		s.render();
	}
}
