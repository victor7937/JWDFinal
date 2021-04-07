package by.victor.jwd.controller.command.impl;

import by.victor.jwd.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteCartItem implements Command {

    private static final String ART_PREFIX = "art_";
    private static final String DELIMITER = "|";
    private static final String REGEX_DELIMITER = "\\|";
    private static final String COMMAND_GOTOCART = "Controller?command=gotocart";
    private static final String ART_PARAM = "art";
    private static final String SIZE_PARAM = "size";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String art = request.getParameter(ART_PARAM);
        String size = request.getParameter(SIZE_PARAM);
        Cookie[] cookies = request.getCookies();

        if (cookies == null || art == null || "".equals(art) || size == null || "".equals(size)) {
            response.sendRedirect(COMMAND_GOTOCART);
            return;
        }

        for (Cookie cookie : cookies) {
            if ((ART_PREFIX + art).equals(cookie.getName())) {
                if (cookie.getValue().equals(size)) {
                   cookie.setMaxAge(0);
                   response.addCookie(cookie);
                } else if (cookie.getValue().contains(size)) {
                    List<String> sizes = Arrays.stream(cookie.getValue()
                            .split(REGEX_DELIMITER))
                            .collect(Collectors.toList());
                    sizes.remove(size);
                    cookie.setValue(String.join(DELIMITER,sizes));
                    response.addCookie(cookie);
                }
            }
        }
        response.sendRedirect(COMMAND_GOTOCART);
    }
}
