package Warcraft.ui;

import Warcraft.screen.Screen;
import Warcraft.tools.Vec2;

import java.awt.*;

public abstract class ButtonCircle extends UiElement {

    private final String image;
    private final String c;

    /**
     * @param pos  the position
     * @param size
     */
    public ButtonCircle(Vec2 pos, double size, String image, String c) {
        super(pos, size);
        this.image = image;
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

        screen.drawFilledCircleAbsolute(getPos(), getSize()*scale, colorCircle);


        screen.drawCircleAbsolute(getPos(), getSize()*scale, new Color(0xD35400));
        screen.drawImageAbsolute(getPos(), new Vec2(getSize()*.6*2*scale, getSize()*.6*2*scale), image, 0);
        screen.drawTextLeftAbsolute(getPos().add(new Vec2(-getSize(), getSize())), 20, Color.black, c);
    }
}
