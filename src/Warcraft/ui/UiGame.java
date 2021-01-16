package Warcraft.ui;

import Warcraft.level.Level;
import Warcraft.screen.Screen;
import Warcraft.tools.InputHandler;
import Warcraft.tools.Vec2;
import Warcraft.tools.Vec2i;

import java.util.ArrayList;
import java.util.List;

public class UiGame {
    private Level level;
    private Screen screen;
    private List<UiElement> elements;
    private boolean debug;
    private List<UiElement> debugElements;

    public UiGame(Level level, Screen screen){
        this.level = level;
        this.screen = screen;
        this.elements = new ArrayList<>();
        this.debugElements = new ArrayList<>();

        elements.add(new ButtonArcher(new Vec2(.6, .05)));
        elements.add(new ButtonBomb(new Vec2(.4, .05)));
        elements.add(new ButtonUpgrade(new Vec2(.5, .05)));
        elements.add(new ButtonSpeedUp(new Vec2(.13, .03)));
        elements.add(new ButtonPause(new Vec2(.05, .05)));

        debugElements.add(new ButtonKill(new Vec2(.06, .5)));
        debugElements.add(new ButtonNextWave(new Vec2(.06,  .40)));
        debugElements.add(new ButtonSpawnMonster(new Vec2(.06,  .60)));
        debugElements.add(new ButtonCoins(new Vec2(.06,  .70)));
        debugElements.add(new ButtonLives(new Vec2(.06,  .80)));
    }

    public void setDebug(boolean d){
        this.debug = d;
    }

    public void update(){
        for(UiElement e : elements){
            e.handleMouse(level);
        }
        if(debug){
            for(UiElement e : debugElements)
                e.handleMouse(level);
        }
    }

    public void draw(){
        for(UiElement e : elements){
            e.onDraw(screen);
        }
        if(debug){
            for(UiElement e : debugElements)
                e.onDraw(screen);
        }
    }
}
