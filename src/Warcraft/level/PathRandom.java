package Warcraft.level;

import Warcraft.screen.Screen;
import Warcraft.screen.textures.Texture;
import Warcraft.tools.Vec2;
import Warcraft.tools.Vec2i;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * This class handle the path of the monsters
 * The data are a list of vectors between each points
 */
public class PathRandom {
    private final List<Vec2i> path;
    private final Vec2i start;
    private final Screen screen;
    private int cntDraw;

    /**
     * @param size      the size x and y
     * @param start     the start position
     */
    public PathRandom(Vec2i size, Vec2i start, Screen screen){
        this.start = start;
        this.screen = screen;
        cntDraw = 0;
        path = generate(size, start);
        
        drawPath();
    }

    /**
     * Show a 2D array on the console
     * @param tiles     the 2D array
     */
    private void print(int[][] tiles){
        for(int i=tiles.length-1; i>=0; i--){
            for(int j=0; j<tiles[i].length; j++){
                String s = String.valueOf(tiles[i][j]);
                if(s.equals("0"))
                    s = "";
                for(int k=s.length(); k<3; k++)
                    s = " " + s;

                System.out.print(s);
            }
            System.out.println();
        }
    }

    /**
     * Get the position of a monster at a time t
     * @param t     the time t
     * @return      the position on the path
     */
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

    /**
     * @return the length of the path in tiles
     */
    public int length(){
        return path.size()*2;
    }

    /**
     * @param size      the size of the array
     * @param start     the starting point
     * @return          the vectors of the path
     */
    private List<Vec2i> generate(Vec2i size, Vec2i start){
        int[][] tiles = new int[size.y/2][size.x/2];

        //Generate maze
        generate_int(tiles, start.div(2), 1);
    
        print(tiles);

        //Find longest path
        Vec2i max = getMax(tiles);
        assert(max != null);

        //Trace back the path from max to zero
        List<Vec2i> list = new ArrayList<>(goBack(tiles, max));
        for(int i=0; i<list.size(); i++)
            list.set(i, list.get(i).mul(-2)); // *2 for all

        return list;
    }

    /**
     * Find the max in the 2D array
     * @param tab the 2D array
     * @return    the position of the max
     */
    private Vec2i getMax(int[][] tab){
        Vec2i max = null;
        for(int i=0; i<tab.length; i++){
            for(int j=0; j<tab[0].length; j++){
                if(max == null || tab[i][j] > tab[max.y][max.x])
                    max = new Vec2i(j, i);
            }
        }
        return max;
    }

    /**
     * Trace back a path
     * @param tiles     A 2D array
     * @param max       The starting point
     * @return          The list of vector from the max to the end of the path
     */
    private List<Vec2i> goBack(int[][] tiles, Vec2i max){
        LinkedList<Vec2i> list = new LinkedList<>();
        
        Vec2i current = max;
        
        list.push(new Vec2i());
    
        for(int i=tiles[max.y][max.x]; i>1; i--){
            Vec2i newCurrent = findNext(tiles, current);
            list.push(newCurrent.sub(current));
            current = newCurrent;
        }
        
        return list;
    }

    /**
     * Find the position next to the current with number-1
     * @param tiles         A 2D array
     * @param current       The current pos
     * @return              The finded pos
     */
    private Vec2i findNext(int[][] tiles, Vec2i current) {
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

    /**
     * Generate a perfect maze (recursive)
     * @param tiles     A 2D array
     * @param current   The current position
     * @param n         The level of depth in the recursion
     */
    private void generate_int(int[][] tiles, Vec2i current, int n){
        int dir = (int)(Math.random()*4)%4;

        //Stop condition
        if(!isFree(tiles, current))
            return;

        tiles[current.y][current.x] = n;
        //drawMaze(tiles);

        //Search in all dir randomly
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

    /**
     * @param tiles A 2D array
     * @param pos   The current position
     * @return      If the tile is available for a path
     */
    private boolean isFree(int[][] tiles, Vec2i pos){
        if(pos.x < 0 || pos.y < 0 || pos.x >= tiles[0].length || pos.y >= tiles.length)
            return false;

        return (tiles[pos.y][pos.x] == 0);
    }
    
    /**
     * Draw the animation at the creation of the maze used for the path
     * @param tiles
     */
    private void drawMaze(int[][] tiles){
        
        if(tiles.length > 10 && cntDraw != tiles.length*10){
            cntDraw++;
            return;
        }
        cntDraw = 0;
        
        screen.clear();
        for(int i=-1; i<tiles.length*2; i++){
            for(int j=-1; j<tiles[0].length*2; j++){
                Texture t = Texture.BACKGROUND;
                Vec2i p = new Vec2i(j, i);
                
                
                if(!(i == -1 || j == -1)) {
                    if (i % 2 == 0 && j % 2 == 0 && tiles[i / 2][j / 2] != 0)
                        t = Texture.PATH;
                    else if (j % 2 == 1 && i % 2 == 0) {
                        //Check up down
                        if (j / 2 < tiles[0].length - 1) {
                            int a = tiles[i / 2][j / 2];
                            if (a != 0) {
                                int b = tiles[i / 2][j / 2 + 1];
                                if (Math.abs(a - b) == 1)
                                    t = Texture.PATH;
                            }
                        }
                    } else if (i % 2 == 1 && j % 2 == 0) {
                        //Check left right
                        if (i / 2 < tiles.length - 1) {
                            int a = tiles[i / 2][j / 2];
                            if (a != 0) {
                                int b = tiles[i / 2 + 1][j / 2];
                                if (Math.abs(a - b) == 1)
                                    t = Texture.PATH;
                            }
                        }
        
                    }
                }
                
                t.draw(screen, new Vec2(p.add(1)), 1, 0);
            }
        }
        screen.render();
    }
    
    /**
     * Draw the animation only for the path
     */
    private void drawPath(){
        screen.clear();
        
        for(int i=0; i<screen.getnTiles().y+1; i++){
            for(int j=0; j<screen.getnTiles().x; j++){
                Texture.BACKGROUND.draw(screen, new Vec2(j, i), 1, 0);
            }
        }
        
        int wait = 1000 / length();
        
        for(int i=0; i<length(); i++){
            Texture.PATH.draw(screen, get(i), 1, 0);
            screen.render();
    
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
