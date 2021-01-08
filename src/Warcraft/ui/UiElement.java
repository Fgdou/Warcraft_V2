package Warcraft.ui;

import Warcraft.level.Level;
import Warcraft.screen.Screen;
import Warcraft.tools.InputHandler;
import Warcraft.tools.Vec2;

public abstract class UiElement {

    private Vec2 pos;
    private double size;
    private boolean mouseEntered;
    private boolean mousePressed;
    private boolean visible;
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


    /**
     * @param pos the position
     */
    public UiElement(Vec2 pos, double size){
        this.pos = pos;
        this.size = size;
        mouseEntered = false;
        mousePressed = false;
        visible = true;
        enabled = true;
    }

    /**
     * @return the absolute position of the element
     */
    public Vec2 getPos(){
        return pos;
    }

    public boolean isPress(){
        return mousePressed;
    }
    public boolean isHover(){
        return mouseEntered;
    }
    public double getSize(){
        return size;
    }

    /**
     * Draw the button to the screen
     * @param screen        the main class for drawing
     */
    public abstract void onDraw(Screen screen);

    /**
     * Will be called when the mouse click the element
     */
    public void onMouseClicked(Level level){
        level.getInputHandler().getMouseClicked();
    }

    /**
     * When the mouse is pressed
     */
    public void onMouseDown(Level level){

    }

    /**
     * When the mouse is released
     */
    public void onMouseUp(Level level){

    }

    /**
     * When the cursor enter the element
     */
    public void onMouseEnter(Level level){

    }

    /**
     * When the cursor leave the element
     */
    public void onMouseLeave(Level level){

    }

    /**
     * Handle the mouse to call the functions
     * @param level
     */
    public void handleMouse(Level level){
        InputHandler input = level.getInputHandler();
        if(!isEnabled())
            return;

        boolean mouseDown = input.getMousePressed();
        Vec2 pos = input.getMouseAbsolute();

        if(pos.distance(this.pos) <= size){
            if(!mouseEntered){
                onMouseEnter(level);
                mouseEntered = true;
            }
            if(mouseDown){
                if(!mousePressed){
                    onMouseDown(level);
                    mousePressed = true;
                }
            }else{
                if(mousePressed){
                    onMouseUp(level);
                    onMouseClicked(level);
                    mousePressed = false;
                }
            }

        }else{
            if(mouseEntered){
                onMouseLeave(level);
                mouseEntered = false;
            }
            if(mousePressed){
                onMouseUp(level);
                mousePressed = false;
            }
        }
    }
}
