package Warcraft.ui;

import Warcraft.level.Level;
import Warcraft.screen.Screen;
import Warcraft.tools.InputHandler;
import Warcraft.tools.Vec2;
import Warcraft.tools.Vec2i;

import java.util.ArrayList;
import java.util.List;

public class Ui {
    private Level level;
    private Screen screen;
    private List<UiElement> elements;

    public Ui(Level level, Screen screen){
        this.level = level;
        this.screen = screen;
        this.elements = new ArrayList<>();

        elements.add(new ButtonArcher(new Vec2(.6, .05)));
        elements.add(new ButtonBomb(new Vec2(.4, .05)));
        elements.add(new ButtonUpgrade(new Vec2(.5, .05)));
        elements.add(new ButtonSpeedUp(new Vec2(.13, .03)));
        elements.add(new ButtonPause(new Vec2(.05, .05)));
    }

    public void update(){
        for(UiElement e : elements){
            e.handleMouse(level);
        }
    }

    public void draw(){
        for(UiElement e : elements){
            e.onDraw(screen);
        }
    }
}
