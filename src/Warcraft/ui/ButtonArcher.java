package Warcraft.ui;

import Warcraft.entities.towers.TowerArcher;
import Warcraft.entities.towers.TowerArcher1;
import Warcraft.entities.towers.TowerBomb1;
import Warcraft.level.Level;
import Warcraft.tools.Vec2;

public class ButtonArcher extends ButtonCircle {
    /**
     * @param pos   the position
     */
    public ButtonArcher(Vec2 pos) {
        super(pos, .04, "/assets/images/arrow.png", "a");
    }

    @Override
    public void onMouseClicked(Level level) {
        super.onMouseClicked(level);

        if(level.getState() == Level.State.NewTower && level.getNewTower() instanceof TowerArcher){
            level.setState(Level.State.Normal);
        }else {
            level.setState(Level.State.NewTower);
            level.setNewTower(new TowerArcher1(getPos()));
        }
    }
}
