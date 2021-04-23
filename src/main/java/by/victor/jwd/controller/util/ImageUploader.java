package by.victor.jwd.controller.util;

import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Util class for uploading and deleting images.
 */
public class ImageUploader {

    private static final ResourceBundle db_bundle = ResourceBundle.getBundle("application");
    private static final String IMAGE_CONTAINER_PATH = db_bundle.getString("path.to.image.container");
    private static final String FILE_PARAM = "file";
    private static final String SUFFIX = ".webp";
    private static final String IMAGE_PREFIX = "footwear_image_";
    private static final int MIN_LENGTH = 30;
    private static final int MAX_LENGTH = 60;

    /**
     * Upload method gets images from request, generates unique names for them
     * and load them to image container.
     * @param request - request which contains images
     * @return list with file names
     * @throws ServletException
     * @throws IOException
     */
    public static List<String> upload (HttpServletRequest request) throws ServletException, IOException {
        List<String> fileNames = new ArrayList<>();
        List<Part> fileParts = request.getParts().stream()
                .filter(part -> FILE_PARAM.equals(part.getName()) && part.getSize() > 0)
                .collect(Collectors.toList());

        for (Part filePart : fileParts) {
            File uploads = new File(IMAGE_CONTAINER_PATH);
            String prefix = IMAGE_PREFIX + RandomStringUtils.randomAlphanumeric(MIN_LENGTH, MAX_LENGTH);
            File file = File.createTempFile(prefix, SUFFIX, uploads);
            fileNames.add(file.getName());
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }

        return fileNames;
    }

    /**
     * Delete image by name.
     * @param filename - name of image
     * @return - true if image was deleted, else false
     * @throws IOException
     */
    public static boolean delete(String filename) throws IOException {
        File file = new File(IMAGE_CONTAINER_PATH, filename);
        return file.delete();
    }

}
