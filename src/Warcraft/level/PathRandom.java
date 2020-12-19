package Warcraft.level;

import Warcraft.tools.Vec2;
import Warcraft.tools.Vec2i;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class PathRandom {
    private List<Vec2i> path;
    private Vec2i start;

    public PathRandom(Vec2i size, Vec2i start){
        path = generate(size, start);
        this.start = start;
    }

    private static void print(int[][] tiles){
        for(int i=tiles.length-1; i>=0; i--){
            for(int j=0; j<tiles[i].length; j++){
                String s = String.valueOf(tiles[i][j]);
                if(s.equals("0"))
                    s = "";
                for(int k=s.length(); k<3; k++)
                    s = " " + s;

                System.out.print(s);
            }
            System.out.println("");
        }
    }

    public Vec2 get(double t){
        t /= 2;
        int index = 0;
        Vec2i current = new Vec2i(start);
        while(index <= t-1){
            if(index >= path.size())
                return null;
            current = current.add(path.get(index));
            index++;
        }

        if(index+1 <= path.size())
            return (new Vec2(current)).add(new Vec2(path.get(index)).mul(t - index));
        else return new Vec2(current);
    }

    public int length(){
        return path.size()*2;
    }

    private static List<Vec2i> generate(Vec2i size, Vec2i start){
        int[][] tiles = new int[size.y/2][size.x/2];
        generate_int(tiles, start, 1);

        print(tiles);

        Vec2i max = getMax(tiles);
        assert(max != null);

        List<Vec2i> list = new ArrayList<>(goBack(tiles, max));
        for(int i=0; i<list.size(); i++)
            list.set(i, list.get(i).mul(-2));
        System.out.println(list);
        return list;
    }
    private static Vec2i getMax(int[][] tab){
        Vec2i max = null;
        for(int i=0; i<tab.length; i++){
            for(int j=0; j<tab[0].length; j++){
                if(max == null || tab[i][j] > tab[max.y][max.x])
                    max = new Vec2i(j, i);
            }
        }
        return max;
    }
    private static List<Vec2i> goBack(int[][] tiles, Vec2i max){
        LinkedList<Vec2i> list = new LinkedList<>();

        Vec2i current = max;
        for(int i=tiles[max.y][max.x]; i>1; i--){
            Vec2i newCurrent = findNext(tiles, current);
            list.push(newCurrent.sub(current));
            current = newCurrent;
        }
        return list;
    }

    private static Vec2i findNext(int[][] tiles, Vec2i current) {
        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++) {
                if (i == 0 && j == 0)
                    continue;
                Vec2i p = current.add(new Vec2i(j, i));

                if (p.x < 0 || p.y < 0)
                    continue;
                if (p.y >= tiles.length || p.x >= tiles[0].length)
                    continue;

                if (tiles[p.y][p.x] == tiles[current.y][current.x] - 1)
                    return p;
            }
        }
        return null;
    }

    private static void generate_int(int[][] tiles, Vec2i current, int n){
        int dir = (int)(Math.random()*4)%4;

        if(!isFree(tiles, current))
            return;

        tiles[current.y][current.x] = n;

        for(int i=0; i<4; i++){
            int d = (dir+i)%4;

            Vec2i next = new Vec2i(current);
            if(d == 0)
                next.x++;
            else if(d == 1)
                next.y++;
            else if(d == 2)
                next.y--;
            else
                next.x--;

            generate_int(tiles, next, n+1);
        }
    }
    private static boolean isFree(int[][] tiles, Vec2i pos){
        if(pos.x < 0 || pos.y < 0 || pos.x >= tiles[0].length || pos.y >= tiles.length)
            return false;

        return (tiles[pos.y][pos.x] == 0);
    }
}
