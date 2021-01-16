package Warcraft.ui;

import Warcraft.entities.monsters.Monster;
import Warcraft.level.Level;
import Warcraft.tools.Vec2;

public class ButtonSpawnMonster extends ButtonRectangle {
    public ButtonSpawnMonster(Vec2 pos) {
        super(pos, .05, "Monsters", null);
    }

    @Override
    public void onMouseClicked(Level level){
        for(int i=0; i<10; i++)
            level.addEntity(Monster.getByName(""));
    }
}
