package Warcraft.ui;

import Warcraft.level.Level;
import Warcraft.tools.Vec2;

public class ButtonPause extends ButtonCircle {
    /**
     * @param pos   the position
     */
    public ButtonPause(Vec2 pos) {
        super(pos, .04, null, null);
    }

    @Override
    public void onMouseClicked(Level level) {
        super.onMouseClicked(level);

        level.togglePause();
    }
}
