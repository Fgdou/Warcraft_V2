package Warcraft.ui;

import Warcraft.entities.towers.TowerBomb;
import Warcraft.entities.towers.TowerBomb1;
import Warcraft.level.Level;
import Warcraft.tools.Vec2;

public class ButtonBomb extends ButtonCircle {
    /**
     * @param pos   the position
     */
    public ButtonBomb(Vec2 pos) {
        super(pos, .04, "/assets/images/bomb.png", "b");
    }

    @Override
    public void onMouseClicked(Level level) {
        super.onMouseClicked(level);

        if(level.getState() == Level.State.NewTower && level.getNewTower() instanceof TowerBomb){
            level.setState(Level.State.Normal);
        }else {
            level.setState(Level.State.NewTower);
            level.setNewTower(new TowerBomb1(getPos()));
        }
    }
}
