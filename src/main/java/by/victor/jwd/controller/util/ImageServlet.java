package by.victor.jwd.controller.util;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ResourceBundle;

/**
 * Servlet for getting images from container
 */
@WebServlet(name = "ImageServlet", value = "/images/*")
public class ImageServlet extends HttpServlet {

    private static final ResourceBundle db_bundle = ResourceBundle.getBundle("application");
    private static final String IMAGE_CONTAINER_PATH = db_bundle.getString("path.to.image.container");
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String INLINE_FILENAME = "inline; filename=\"";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = URLDecoder.decode(request.getPathInfo().substring(1), StandardCharsets.UTF_8);
        File file = new File(IMAGE_CONTAINER_PATH, filename);
        response.setHeader(CONTENT_TYPE, getServletContext().getMimeType(filename));
        response.setHeader(CONTENT_LENGTH, String.valueOf(file.length()));
        response.setHeader(CONTENT_DISPOSITION, INLINE_FILENAME + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
