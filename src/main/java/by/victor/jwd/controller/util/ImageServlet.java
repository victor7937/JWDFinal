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

@WebServlet(name = "ImageServlet", value = "/images/*")
public class ImageServlet extends HttpServlet {
    private static final ResourceBundle db_bundle = ResourceBundle.getBundle("application");
    private static final String IMAGE_CONTAINER_PATH = db_bundle.getString("path.to.image.container");


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = URLDecoder.decode(request.getPathInfo().substring(1), StandardCharsets.UTF_8);
        File file = new File(IMAGE_CONTAINER_PATH, filename);
        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
