package Warcraft.entities.animations;

import Warcraft.screen.textures.TextureImage;
import Warcraft.screen.textures.TextureTextRight;
import Warcraft.tools.Vec2;

public class AnimationText extends Animation {

    /**
     * @param pos     The position
     * @param speed   The speed
     * @param texture
     */
    public AnimationText(Vec2 pos, String text, String image) {
        super(pos, .005, new TextureTextRight(text, new TextureImage(image)));
    }
}
