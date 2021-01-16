package Warcraft.ui;

import Warcraft.level.Level;
import Warcraft.tools.Vec2;

public class ButtonCoins extends ButtonRectangle {
    public ButtonCoins(Vec2 pos) {
        super(pos, .05, "Coins", null);
    }

    @Override
    public void onMouseClicked(Level level) {
        super.onMouseClicked(level);
        level.addCoins(100);
    }
}
