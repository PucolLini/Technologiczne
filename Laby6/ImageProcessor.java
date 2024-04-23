package org.example;
import org.apache.commons.lang3.tuple.Pair;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageProcessor {

    public static Pair<String, BufferedImage> loadImage(Path path) throws UncheckedIOException {
        log("Loading image: " + path.getFileName().toString());
        try {
            var filename = path.getFileName().toString();
            var image = ImageIO.read(path.toFile());
            return Pair.of(filename, image);
        } catch (IOException e) {
            throw new UncheckedIOException("Error while loading image", e);
        }
    }

    public static Pair<String, BufferedImage> modifyImage(Pair<String, BufferedImage> input) {
        log("Modifying image: " + input.getLeft());
        BufferedImage original = input.getRight();
        BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());

        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {
                int rgb = original.getRGB(i, j);
                Color color = new Color(rgb);
                int red = color.getRed();
                int blue = color.getBlue();
                int green = color.getGreen();
                Color outColor = new Color(red, blue, green);
                int outRgb = outColor.getRGB();
                result.setRGB(i,j, outRgb);
            }
        }
        return Pair.of(input.getLeft(), result);
    }

    public static void saveImage(Pair<String, BufferedImage> pair, Path outputPath) {
        var imageToSave = pair.getRight();
        var imageName = pair.getLeft();

        log("Saving image: " + imageName);

        try {
            if (!Files.exists(outputPath)) {
                Files.createDirectories(outputPath);
            }
            ImageIO.write(imageToSave, "jpg", outputPath.resolve(imageName).toFile());
            log("Image saved: " + imageName);
        } catch (IOException e) {
            throw new UncheckedIOException("Error while saving image", e);
        }
    }

    private static void log(String message) {
        System.out.println(Thread.currentThread() + " " + message);
    }
}