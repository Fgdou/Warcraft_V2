package Warcraft.ui;

import Warcraft.level.Level;
import Warcraft.tools.Vec2;

public class ButtonLives extends ButtonRectangle {
    public ButtonLives(Vec2 pos) {
        super(pos, .05, "Lives", null);
    }

    @Override
    public void onMouseClicked(Level level) {
        super.onMouseClicked(level);
        level.addLives(10);
    }
}
