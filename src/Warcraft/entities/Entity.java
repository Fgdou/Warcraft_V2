package Warcraft.entities;

import Warcraft.fx.Screen;
import Warcraft.level.Level;

public abstract class Entity {
    private boolean alive;

    public Entity(){
        alive = true;
    }

    public abstract void onTick(Level level);
    public abstract void onDraw(Screen screen);

    public abstract void onInteract(Entity other);
    public boolean isAlive(){
        return alive;
    }
}
