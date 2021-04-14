package by.victor.jwd.controller.util;

import by.victor.jwd.controller.command.CommandName;
import by.victor.jwd.controller.command.CommandPath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ImageUploader {

    private static final ResourceBundle db_bundle = ResourceBundle.getBundle("application");
    private static final String IMAGE_CONTAINER_PATH = db_bundle.getString("path.to.image.container");
    private static final String FILE_PARAM = "file";

    public static List<String> upload (HttpServletRequest request) throws ServletException, IOException {
        List<String> fileNames = new ArrayList<>();
        List<Part> fileParts = request.getParts().stream()
                .filter(part -> FILE_PARAM.equals(part.getName()) && part.getSize() > 0)
                .collect(Collectors.toList());

        for (Part filePart : fileParts) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            fileNames.add(fileName);
            File uploads = new File(IMAGE_CONTAINER_PATH);
            File file = new File(uploads, fileName);
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath());
            }
        }

        return fileNames;
    }
}
