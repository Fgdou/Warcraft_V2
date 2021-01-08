package Warcraft.ui;

import Warcraft.entities.towers.TowerBomb;
import Warcraft.entities.towers.TowerBomb1;
import Warcraft.level.Level;
import Warcraft.tools.Vec2;

public class ButtonUpgrade extends ButtonCircle {
    /**
     * @param pos   the position
     */
    public ButtonUpgrade(Vec2 pos) {
        super(pos, .04, "assets/images/upgrade.png", "e");
    }

    @Override
    public void onMouseClicked(Level level) {
        super.onMouseClicked(level);

        if(level.getState() == Level.State.UpgradeTower){
            level.setState(Level.State.Normal);
        }else {
            level.setState(Level.State.UpgradeTower);
        }
    }
}
