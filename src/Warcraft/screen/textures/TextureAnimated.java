package Warcraft.screen.textures;

import Warcraft.screen.Screen;
import Warcraft.tools.Vec2;

import java.util.Arrays;
import java.util.List;

/**
 * This texture use other textures to animate itself
 */

public class TextureAnimated extends Texture {

    private final List<Texture> textures;
    private int cnt;
    private int iterationTexture;
    private final int timeChange;

    /**
     * @param textures      A list of texture to be changed in the time
     * @param timeChange    The cooldown time for changing the texture in ticks
     */
    TextureAnimated(List<Texture> textures, int timeChange){
        this.textures = textures;
        this.timeChange = timeChange;
        this.iterationTexture = 0;
        cnt = 0;
    }

    /**
     * @param textures      A list of texture to be changed in the time
     * @param timeChange    The cooldown time for changing the texture in ticks
     */
    public TextureAnimated(Texture[] textures, int timeChange){
        this.textures = Arrays.asList(textures);
        this.timeChange = timeChange;
        this.iterationTexture = 0;
        cnt = 0;
    }

    @Override
    public void draw(Screen screen, Vec2 pos, double scale, double angle) {
        cnt++;
        if(cnt == timeChange) {
            iterationTexture = (iterationTexture + 1) % textures.size();
            cnt = 0;
        }

        Texture t = textures.get(iterationTexture);
        t.draw(screen, pos, scale, angle);
    }
}
