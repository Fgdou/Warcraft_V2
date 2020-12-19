package Warcraft.level;

import Warcraft.entities.monsters.Monster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Wave {
    List<List<String>> monsters;
    List<Integer> times;
    List<Integer> number;

    int wave;
    int monster;
    int time;
    boolean startWave = false;

    public Wave(String file) {
        Scanner sc = new Scanner(Wave.class.getResourceAsStream("/" + file));
        monsters = new ArrayList<>();
        times = new ArrayList<>();
        number = new ArrayList<>();
        wave = 0;
        time = 60*5;
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
                time = 5*60;
                startWave = false;
            }else{
                time = times.get(wave);
            }

            System.out.println(wave + " " + time);
            return m;
        }else{
            time--;
        }
        return null;
    }
    public void startWave(){
        startWave = true;
    }
}
