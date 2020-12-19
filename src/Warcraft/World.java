package Warcraft;

import Warcraft.fx.Screen;
import Warcraft.fx.textures.Texture;
import Warcraft.fx.textures.TextureAnimated;
import Warcraft.fx.textures.TextureImage;
import Warcraft.tools.Vec2;
import Warcraft.tools.Vec2i;
import org.w3c.dom.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World {
	public static void main(String[] args) throws InterruptedException {
		Screen s = new Screen(new Vec2i(1200, 600), 10);
		Texture t = new TextureAnimated(new TextureImage[]{
				new TextureImage("assets/images/zombie1.png"),
				new TextureImage("assets/images/zombie3.png"),
				new TextureImage("assets/images/zombie2.png"),
				new TextureImage("assets/images/zombie3.png"),
		}, 60);

		while(true){
			s.clear();
			Vec2 p = new Vec2(2, 2);
			t.draw(s, p, 0.9, 0);
			s.drawCircle(p, 1, Color.black);
			s.render();
			Thread.sleep(1000/60);
		}

	}
}
