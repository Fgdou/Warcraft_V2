package Warcraft.entities;

import Warcraft.fx.Screen;
import Warcraft.fx.textures.Texture;
import Warcraft.level.Level;
import Warcraft.tools.Vec2;

public abstract class Entity {
    private boolean alive;
    private Vec2 pos;
    private Texture texture;

    public Entity(Vec2 pos, Texture texture){
        alive = true;
        this.pos = new Vec2(pos);
        this.texture = texture;
    }

    public void setPos(Vec2 pos){
        this.pos = pos;
    }
    public Vec2 getPos(){
        return pos;
    }
    public void addPos(Vec2 pos){
        this.pos = this.pos.add(pos);
    }

    public Texture getTexture(){
        return texture;
    }

    public abstract void onTick(Level level);
    public abstract void onDraw(Screen screen);

    public void die(){
        alive = false;
    }

    public abstract void onInteract(Entity other);
    public boolean isAlive(){
        return alive;
    }
}
