package Warcraft;

import Warcraft.fx.Screen;
import Warcraft.tools.Vec2;

import java.util.Set;
import java.util.TreeSet;

public class InputHandler {
    private Set<Character> keyboard;
    private Vec2 mouse;
    private Screen screen;

    private boolean mousePressed;
    private boolean mouseClicked;

    public InputHandler(Screen s){
        keyboard = new TreeSet<>();
        mouse = new Vec2(0,0);
        this.screen = s;
        mousePressed = false;
        mouseClicked = false;
    }

    public void tick(){
        mouse.x = StdDraw.mouseX();
        mouse.y = StdDraw.mouseY();

        if(StdDraw.isMousePressed()){
            if(!mousePressed){
                mousePressed = true;
                mouseClicked = true;
            }
        }else{
            mousePressed = false;
        }


        while(StdDraw.hasNextKeyTyped()){
            keyboard.add(StdDraw.nextKeyTyped());
        }
    }
    public Vec2 getMouse(){
        return screen.screenPosToWorld(mouse);
    }
    public Vec2 getMouseAbsolute(){
        return mouse;
    }
    public boolean getMouseClicked(){
        if(mouseClicked){
            mouseClicked = false;
            return true;
        }
        return false;
    }
    public boolean getMousePressed(){
        return mousePressed;
    }

    public boolean getKey(char c){
        if(keyboard.contains(c)){
            keyboard.remove(c);
            return true;
        }
        return false;
    }
}
