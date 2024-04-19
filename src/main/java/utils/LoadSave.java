// Daniil Nikonenko
// PJV Semestral

package utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


public class LoadSave {

    public static final String PLAYER_ATLAS = "/player_sprites.png";
    public static final String CRABBY_ATLAS = "/crabby_sprite.png";
    public static final String LEVEL_ATLAS = "/outside_sprites.png";
    public static final String MENU_BUTTONS_ATLAS = "/button_atlas.png";
    public static final String MAIN_MENU_UI = "/menu_background.png";
    public static final String PAUSE_BACKGROUND = "/pause_menu.png";
    public static final String DEATH_MENU_UI = "/death_screen.png";
    public static final String SOUND_BUTTONS = "/sound_button.png";
    public static final String URM_BUTTONS = "/urm_buttons.png";
    public static final String VOLUME_BUTTONS = "/volume_buttons.png";
    public static final String MENU_BACKGROUND = "background_menu.gif";
    public static final String PLAYING_BACKGROUND = "/playing_bg_img.png";
    public static final String BIG_CLOUDS = "/big_clouds.png";
    public static final String SMALL_CLOUD = "/small_clouds.png";
    public static final String STATUS_BAR = "/health_power_bar.png";
    public static final String LEVEL_COMPLETED_BG = "/completed_sprite.png";
    public static final String CONTAINER_ATLAS = "/objects_sprites.png";
    public static final String POTION_ATLAS = "/potions_sprites.png";
    public static final String SPIKE_ATLAS = "/trap_atlas.png";
    public static final String CANNON_ATLAS = "/cannon_atlas.png";
    public static final String BALL = "/ball.png";
    public static final String GRASS_ATLAS = "/grass_atlas.png";


    public static BufferedImage GetSpriteAtlas(final String FilePath) {
        BufferedImage img;
        InputStream is = LoadSave.class.getResourceAsStream(FilePath);
        try {
            img = ImageIO.read(Objects.requireNonNull(is));
        } catch (IOException e) {
            // TO DO catch
            throw new RuntimeException(e);
        }
        return img;
    }

    public static BufferedImage[] GetGIF(String filePath) {
        try {
            InputStream inputStream = LoadSave.class.getClassLoader().getResourceAsStream(filePath);
            ImageInputStream stream = ImageIO.createImageInputStream(Objects.requireNonNull(inputStream));
            Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);

            if (!readers.hasNext()) {
                throw new IllegalArgumentException("No image reader found for GIF format");
            }

            ImageReader reader = readers.next();
            reader.setInput(stream);

            int numImages = reader.getNumImages(true);
            List<BufferedImage> frames = new ArrayList<>();

            for (int i = 0; i < numImages; i++) {
                BufferedImage image = reader.read(i);
                frames.add(image);
            }

            stream.close();
            reader.dispose();

            return frames.toArray(new BufferedImage[0]);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
