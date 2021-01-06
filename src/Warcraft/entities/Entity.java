package Warcraft.entities;

import Warcraft.screen.Screen;
import Warcraft.screen.textures.Texture;
import Warcraft.level.Level;
import Warcraft.tools.Vec2;

/**
 * This is the main class of all object in the game.
 * Towers and Monsters are children of this class.
 */
public abstract class Entity {
    private boolean alive;
    private Vec2 pos;
    private Texture texture;

    /**
     * @param pos       The starting point
     * @param texture   The texture to draw
     */
    public Entity(Vec2 pos, Texture texture){
        alive = true;
        this.pos = new Vec2(pos);
        this.texture = texture;
    }

    /**
     * Set the position of the entity
     * @param pos
     */
    public void setPos(Vec2 pos){
        this.pos = pos;
    }

    /**
     * @return the position of the entity
     */
    public Vec2 getPos(){
        return pos;
    }

    /**
     * Move the player in a direction
     * @param pos the direction
     */
    public void addPos(Vec2 pos){
        this.pos = this.pos.add(pos);
    }

    /**
     * @return the texture of the entity
     */
    public Texture getTexture(){
        return texture;
    }

    /**
     * Will be called on the children to execute actions
     * @param level     the main level for getting information on the world
     */
    public abstract void onTick(Level level);

    /**
     * Will be called on the children to draw themself
     * @param screen    the main screen for drawing
     */
    public abstract void onDraw(Screen screen);

    /**
     * Kill the entity
     */
    public void die(){
        alive = false;
    }

    /**
     * Will be called on children to execute actions, like shooting
     * @param other     the target
     * @param level     the main level for information
     */
    public abstract void onInteract(Entity other, Level level);

    /**
     * @return if the entity is alive
     */
    public boolean isAlive(){
        return alive;
    }
}
