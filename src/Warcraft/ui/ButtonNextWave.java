package Warcraft.ui;

import Warcraft.level.Level;
import Warcraft.tools.Vec2;

import java.awt.*;

public class ButtonNextWave extends ButtonRectangle {
    public ButtonNextWave(Vec2 pos) {
        super(pos, .05, "Next W", null);
    }

    @Override
    public void onMouseClicked(Level level) {
        super.onMouseClicked(level);
        level.getWaves().nextWave();
    }
}
