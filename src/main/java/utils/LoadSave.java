package utils;

import entities.Crabby;
import main.Game;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static utils.Constants.EnemyConstants.CRABBY;

public class LoadSave {

    public static final String PLAYER_ATLAS = "/player_sprites.png";
    public static final String CRABBY_ATLAS = "/crabby_sprite.png";
    public static final String LEVEL_ATLAS = "/outside_sprites.png";
//    public static final String LEVEL_ONE_DATA = "/level_one_data.png";
    public static final String LEVEL_ONE_DATA = "/level_one_data_long.png";
    public static final String MENU_BUTTONS_ATLAS = "/button_atlas.png";
    public static final String UI_MENU_BACKGROUND = "/menu_background.png";
    public static final String PAUSE_BACKGROUND = "/pause_menu.png";
    public static final String SOUND_BUTTONS = "/sound_button.png";
    public static final String URM_BUTTONS = "/urm_buttons.png";
    public static final String VOLUME_BUTTONS = "/volume_buttons.png";
    public static final String MENU_BACKGROUND = "background_menu.gif";
    public static final String PLAYING_BACKGROUND = "/playing_bg_img.png";
    public static final String BIG_CLOUDS = "/big_clouds.png";
    public static final String SMALL_CLOUD = "/small_clouds.png";
    public static final String STATUS_BAR = "/health_power_bar.png";




    public static BufferedImage GetSpriteAtlas(final String FilePath){
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream(FilePath);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            // TO DO catch
            throw new RuntimeException(e);
        }
        return  img;
    }

    public static BufferedImage[] GetGIF(String filePath) {
        try {
            InputStream inputStream = LoadSave.class.getClassLoader().getResourceAsStream(filePath);
            ImageInputStream stream = ImageIO.createImageInputStream(inputStream);
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

    public static ArrayList<Crabby> GetCrabs() {
        BufferedImage image = GetSpriteAtlas(LEVEL_ONE_DATA);
        ArrayList<Crabby> list = new ArrayList<>();

        for (int j = 0; j < image.getHeight(); j++)
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == CRABBY)
                    list.add(new Crabby(i * Game.TILES_SIZE, j * Game.TILES_SIZE));

            }

        return list;
    }

    public static int[][] GetLevelData(){
        BufferedImage image = GetSpriteAtlas(LEVEL_ONE_DATA);
        int[][] levelData =  new int[image.getHeight()][image.getWidth()];


        for (int j = 0; j < image.getHeight(); j++)
            for (int i = 0; i < image.getWidth(); i++){
                Color color = new Color(image.getRGB(i,j));
                int value = color.getRed();

                if(value >= 48) value = 0;
                levelData[j][i] = value;
            }
        return levelData;
    }
}
