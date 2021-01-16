package Warcraft.ui;

import Warcraft.level.Level;
import Warcraft.tools.Vec2;

public class ButtonKill extends ButtonRectangle {
    public ButtonKill(Vec2 pos) {
        super(pos, .05, "kill", null);
    }

    @Override
    public void onMouseClicked(Level level) {
        super.onMouseClicked(level);
        level.killMonsters();
    }
}
