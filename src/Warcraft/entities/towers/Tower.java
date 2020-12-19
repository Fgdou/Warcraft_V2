package Warcraft.entities.towers;

import Warcraft.entities.Entity;
import Warcraft.fx.Screen;
import Warcraft.fx.textures.Texture;
import Warcraft.level.Level;
import Warcraft.tools.Vec2;

public abstract class Tower extends Entity {
    private int pv;

    public Tower(Vec2 pos, Texture texture, int pv) {
        super(pos, texture);
        this.pv = pv;
    }

    @Override
    public void onTick(Level level){
        //TODO
    }
    @Override
    public void onDraw(Screen screen){
        getTexture().draw(screen, getPos(), 1, 0);
    }
    @Override
    public void onInteract(Entity e){
        //TODO
    }
    @Override
    public void die(){
        pv = 0;
    }
    @Override
    public boolean isAlive(){
        return (pv > 0);
    }

    public void hurt(int damage){
        pv -= damage;
    }
}
