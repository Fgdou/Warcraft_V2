package Warcraft.level;

import Warcraft.entities.monsters.Monster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Wave {
    private List<List<String>> monsters;
    private List<Integer> times;
    private List<Integer> number;

    private final int COOLDOWN = 10*60;

    private int wave;
    private int monster;
    private int time;
    private boolean startWave = false;

    public Wave(String file) {
        Scanner sc = new Scanner(Wave.class.getResourceAsStream("/" + file));
        monsters = new ArrayList<>();
        times = new ArrayList<>();
        number = new ArrayList<>();
        wave = 0;
        time = COOLDOWN;
        monster = 0;

        List<String> current = new ArrayList<>();
        List<String> last = new ArrayList<>();
        while(sc.hasNextLine()){
            String line = sc.nextLine();

            if(line.startsWith("wave")){
                current = new ArrayList<>(last);
                last = current;
                monsters.add(current);
                String[] tab = line.split(" ");
                number.add(Integer.parseInt(tab[1]));
                times.add(Integer.parseInt(tab[2]));
            }else if(line.startsWith("boss")){
                current = new ArrayList<>();
                monsters.add(current);
                String[] tab = line.split(" ");
                number.add(Integer.parseInt(tab[1]));
                times.add(Integer.parseInt(tab[2]));
            }else if(line.equals(""))
                continue;
            else{
                current.add(line);
            }
        }
    }

    public boolean hasNext(){
        return (wave < monsters.size());
    }
    public Monster getNext(){
        if(!startWave)
            return null;
        if(time <= 0 && hasNext()){

            int n = (int)(Math.random()*monsters.get(wave).size());
            Monster m = Monster.getByName(monsters.get(wave).get(n));
            monster++;

            if(monster >= number.get(wave)){
                wave++;
                time = COOLDOWN;
                startWave = false;
                monster = 0;
            }else{
                time = times.get(wave);
            }

            return m;
        }else{
            time--;
        }
        return null;
    }
    public void startWave(){
        startWave = true;
    }

    public double getPercent() {
        if(!startWave || monster == 0 || wave >= monsters.size())
            return 1.0 - ((double) time / (COOLDOWN));
        return 1.0 - ((double)monster / number.get(wave));
    }
}
