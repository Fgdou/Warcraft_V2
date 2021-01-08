package Warcraft.entities.animations;

import Warcraft.entities.Entity;
import Warcraft.level.Level;
import Warcraft.screen.Screen;
import Warcraft.screen.textures.Texture;
import Warcraft.tools.Vec2;

public abstract class Animation extends Entity {

    private double speed;
    private Vec2 pos;
    private int cnt;

    /**
     * @param pos       The position
     * @param speed     The speed
     */
    public Animation(Vec2 pos, double speed, Texture texture){
        super(pos, texture);
        this.speed = speed;
        cnt = 0;
    }

    @Override
    public void onTick(Level level) {
        addPos(new Vec2(0, speed));
        cnt++;

        if(cnt == 60*2)
            die();
    }

    @Override
    public void onDraw(Screen screen) {
        getTexture().draw(screen, getPos(), 1, 0);
    }

    @Override
    public void onInteract(Entity other, Level level) {
    }
}
