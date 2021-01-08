package Warcraft.ui;

import Warcraft.level.Level;
import Warcraft.tools.Vec2;

public class ButtonSpeedUp extends ButtonCircle {
    /**
     * @param pos   the position
     */
    public ButtonSpeedUp(Vec2 pos) {
        super(pos, .02, null, null);
    }

    @Override
    public void onMouseDown(Level level) {
        super.onMouseDown(level);

        level.setSpeed(10);
    }

    @Override
    public void onMouseUp(Level level) {
        super.onMouseUp(level);

        level.setSpeed(1);
    }
}
