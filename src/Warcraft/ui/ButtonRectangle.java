package Warcraft.ui;

import Warcraft.screen.Screen;
import Warcraft.tools.Vec2;

import java.awt.*;

public class ButtonRectangle extends UiElement {
    private final String text;
    private final String c;

    /**
     * @param pos  the position
     * @param size
     */
    public ButtonRectangle(Vec2 pos, double size, String text, String c) {
        super(pos, size);
        this.text = text;
        this.c = c;
    }

    @Override
    public void onDraw(Screen screen) {
        if(!isVisible())
            return;

        Color colorCircle = new Color(0xe67e22);
        double scale = 1;

        if(isPress()) {
            colorCircle = new Color(0xD35400);
            scale = .9;
        }else if(isHover())
            colorCircle = new Color(0xD35400);

        screen.drawFilledRectangleAbsolute(getPos(), new Vec2(getSize()*scale, getSize()*.4*scale), colorCircle);


        screen.drawRectangleAbsolute(getPos(), new Vec2(getSize()*scale, getSize()*.4*scale), new Color(0xD35400));
        screen.drawTextCenterAbsolute(getPos(), 20, Color.black, text);
        screen.drawTextLeftAbsolute(getPos().add(new Vec2(-getSize(), getSize())), 20, Color.black, c);
    }
}
