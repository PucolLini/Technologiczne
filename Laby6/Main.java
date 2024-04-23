package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.ImageProcessor.loadImage;
import static org.example.ImageProcessor.modifyImage;

public class Main {
    public static void main(String[] args) {
        var inputPathStr = args.length >= 1 ? args[0] : "./img";
        var outputPathStr = args.length >= 2 ? args[1] : "./new_img";
        var threads = args.length >= 3 ? Integer.parseInt(args[2]) : Runtime.getRuntime().availableProcessors();

        System.out.println("Input path: " + inputPathStr + ", output path: " + outputPathStr + ", threads: " + threads);

        var inputPath = Path.of(inputPathStr);
        var outputPath = Path.of(outputPathStr);

        List<Path> files;
        try (Stream<Path> stream = Files.list(inputPath)) {
            files = stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Error while reading files!");
            return;
        }

        long startTime = System.currentTimeMillis();

        ForkJoinPool threadPool = new ForkJoinPool(2);

        threadPool.submit(() ->
                files.parallelStream()
                        .map(path -> {
                            return loadImage(path);
                        })
                        .filter(image -> image != null)
                        .map(image -> modifyImage(image))
                        .forEach(modifiedImage -> {
                            ImageProcessor.saveImage(modifiedImage, outputPath);
                        })).join();

        threadPool.shutdown();

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }

}
