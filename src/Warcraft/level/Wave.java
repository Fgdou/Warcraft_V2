package Warcraft.level;

import Warcraft.entities.monsters.Monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class is used for the spawn of the monsters
 */

public class Wave {
    private final List<List<String>> monstersPerWave;
    private final List<Integer> cooldowns;
    private final List<Integer> nbMonsters;

    private final int COOLDOWN = 10*60;

    private int wave;
    private int monster;
    private int time;
    private boolean startWave = false;

    /**
     * @param file the file to read the wave
     */
    public Wave(String file) {
        monstersPerWave = new ArrayList<>();
        cooldowns = new ArrayList<>();
        nbMonsters = new ArrayList<>();
        wave = 0;
        time = COOLDOWN;
        monster = 0;

        parseFile(file);
    }

    /**
     * Parse the file to waves of monsters
     * @param file
     */
    private void parseFile(String file) {
        Scanner sc = new Scanner(Wave.class.getResourceAsStream("/" + file));

        List<String> current = new ArrayList<>();
        List<String> last = new ArrayList<>();
        while(sc.hasNextLine()){
            String line = sc.nextLine();

            if(line.startsWith("wave")){
                current = new ArrayList<>(last);
                last = current;
                monstersPerWave.add(current);
                String[] tab = line.split(" ");
                nbMonsters.add(Integer.parseInt(tab[1]));
                cooldowns.add(Integer.parseInt(tab[2]));
            }else if(line.startsWith("boss")){
                current = new ArrayList<>();
                monstersPerWave.add(current);
                String[] tab = line.split(" ");
                nbMonsters.add(Integer.parseInt(tab[1]));
                cooldowns.add(Integer.parseInt(tab[2]));
            }else if(!line.equals("")){
                current.add(line);
            }
        }
    }

    /**
     * @return if there is any monsters left
     */
    public boolean hasNext(){
        return (wave < monstersPerWave.size());
    }

    /**
     * @return the next monster to add to the level
     * @note return null if this is not the time
     */
    public Monster getNext(){
        if(!startWave)
            return null;
        if(time <= 0 && hasNext()){

            int n = (int)(Math.random()* monstersPerWave.get(wave).size());
            Monster m = Monster.getByName(monstersPerWave.get(wave).get(n));
            monster++;

            //Change the wave
            if(monster >= nbMonsters.get(wave)){
                wave++;
                time = COOLDOWN;
                startWave = false;
                monster = 0;
            }else{
                time = cooldowns.get(wave);
            }

            return m;
        }else{
            time--;
        }
        return null;
    }

    /**
     * Start the wave
     */
    public void startWave(){
        startWave = true;
    }

    /**
     * @return the advancement of the wave, or the advancement of the cooldown
     */
    public double getPercent() {
        if(!startWave || monster == 0 || wave >= monstersPerWave.size())
            return 1.0 - ((double) time / (COOLDOWN));
        return 1.0 - ((double)monster / nbMonsters.get(wave));
    }

    /**
     * @return if the class wait for the next wave
     */
    public boolean isWaitingNextWave() {
        return (startWave && monster == 0);
    }
}
