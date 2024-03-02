package ranger.ui.component;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Class representing a colored icon.
 */
public class ColoredIcon extends ImageIcon {
    /**
     * Constructs a new colored icon.
     * 
     * @param path   The path to the image.
     * @param color  The color.
     * @param width  The width of the scaled image.
     * @param height The height of the scaled image.
     */
    public ColoredIcon(String path, Color color, int width, int height) {
        super(loadImage(path, color, width, height));
    }

    /**
     * Loads an image, scales it and applies a color filter.
     * 
     * @param path   The path to the image.
     * @param color  The color.
     * @param width  The width of the scaled image.
     * @param height The height of the scaled image.
     * @return The image.
     */
    public static Image loadImage(String path, Color color, int width, int height) {
        ClassLoader loader = ColoredIcon.class.getClassLoader();
        URL url = loader.getResource(path);

        if (url == null)
            throw new RuntimeException("Cannot find image '" + path + "' in classpath.");

        try {
            BufferedImage image = ImageIO.read(url);

            if (color != null)
                for (int x = 0; x < image.getWidth(); x++)
                    for (int y = 0; y < image.getHeight(); y++) {
                        int rgb = image.getRGB(x, y);

                        int alpha = (rgb >> 24) & 0xFF;
                        int red = color.getRed();
                        int green = color.getGreen();
                        int blue = color.getBlue();

                        image.setRGB(x, y, new Color(red, green, blue, alpha).getRGB());
                    }

            return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image: " + path, e);
        }
    }

}
