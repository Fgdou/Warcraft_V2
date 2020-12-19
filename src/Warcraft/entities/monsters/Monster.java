package Warcraft.entities.monsters;

import Warcraft.entities.Entity;
import Warcraft.fx.Screen;
import Warcraft.fx.textures.Texture;
import Warcraft.level.Level;
import Warcraft.tools.Vec2;
import Warcraft.tools.Vec2i;

public abstract class Monster extends Entity {
    private int pv;
    private double speed;
    private Vec2 lastPos;
    private double pos;
    private double scale;
    private boolean fly;

    public Monster(Texture texture, int pv, double speed, double scale, boolean fly) {
        super(new Vec2(), texture);
        this.pv = pv;
        this.speed = speed;
        this.pos = 0;
        this.scale = scale;
        this.fly = fly;
        this.lastPos = getPos();
    }

    @Override
    public void onTick(Level level) {
        pos += speed;

        if(pos >= level.getPath().length()-1)
            die();
        //TODO hurt castle

        lastPos = getPos();
        Vec2 actualPos = level.getPath().get(pos);
        setPos(actualPos);

        if(actualPos.equals(new Vec2(level.getCastle())))
            die();
    }
    @Override
    public void onDraw(Screen screen){
        Vec2 delta = getPos().sub(lastPos);
        double angle = 0;

        if(delta.x > 0)
            angle = 90;
        else if(delta.y > 0)
            angle = 180;
        else if(delta.x < 0)
            angle = 270;

        getTexture().draw(screen, getPos(), scale, angle);
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

    public boolean getFly(){
        return fly;
    }
}
