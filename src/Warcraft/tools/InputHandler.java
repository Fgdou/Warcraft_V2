package Warcraft.tools;

import Warcraft.screen.Screen;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class is the interface of the stddraw inputs
 * The mouse and the keyboard
 */

public class InputHandler {
    private final Set<Character> keyboard; // handle many press
    private final Vec2 mouse;
    private final Screen screen;

    private boolean mousePressed;
    private boolean mouseClicked;

    /**
     * @param s   the level screen
     */
    public InputHandler(Screen s){
        keyboard = new TreeSet<>();
        mouse = new Vec2(0,0);
        this.screen = s;
        mousePressed = false;
        mouseClicked = false;
    }

    /**
     * The logic of the class. Update all the variables
     */
    public void tick(){
        mouse.x = StdDraw.mouseX();
        mouse.y = StdDraw.mouseY();

        if(StdDraw.isMousePressed()){
            if(!mousePressed){
                mousePressed = true;
            }
        }else{
            if(mousePressed)
                mouseClicked = true;
            mousePressed = false;
        }


        while(StdDraw.hasNextKeyTyped()){
            keyboard.add(StdDraw.nextKeyTyped());
        }
    }

    /**
     * @return the world coord of the mouse
     */
    public Vec2 getMouse(){
        return screen.screenPosToWorld(mouse);
    }

    /**
     * @return the tile hovered by the mouse
     */
    public Vec2i getMouseTile(){
        return new Vec2i(screen.screenPosToWorldTile(mouse));
    }

    /**
     * @return the StdDraw coord
     */
    public Vec2 getMouseAbsolute(){
        return mouse;
    }

    /**
     * @return if the mouse has clicked since the last call to the function
     */
    public boolean getMouseClicked(){
        if(mouseClicked){
            mouseClicked = false;
            return true;
        }
        return false;
    }

    /**
     * @return if the mouse is press now
     */
    public boolean getMousePressed(){
        return mousePressed;
    }

    /**
     * @param c the key to check
     * @return  if the key has been pressed since the last call with that key
     */
    public boolean getKey(char c){
        if(keyboard.contains(c)){
            keyboard.remove(c);
            return true;
        }
        return false;
    }

    /**
     * @return if there is key in the list
     */
    public boolean hastNextKey(){
        return (keyboard.size() != 0);
    }

    /**
     * @return the next key pressed
     */
    public char getNextKey(){
        Iterator<Character> it = keyboard.iterator();
        char c = it.next();
        it.remove();
        return c;
    }
}
