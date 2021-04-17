package by.victor.jwd.controller.command.impl;

import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.command.CommandName;
import by.victor.jwd.controller.command.CommandPath;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddToCart implements Command {

    private static final String ART_PARAM = "art";
    private static final String SIZE_PARAM = "size";
    private static final String COOKIE_DELIMITER = "|";
    private static final String ART_PREFIX = "art_";
    private static final int MAX_AGE = 60 * 60 * 24;
    private static final String SHOW_PARAM = "show";
    private static final String ADDED_PARAM = "added";
    private static final String YES_VALUE = "yes";
    private static final String NO_SIZE_VALUE = "no_size";
    private static final String NO_VALUE = "no";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String art = request.getParameter(ART_PARAM);
        String size = request.getParameter(SIZE_PARAM);
        if (art == null || "".equals(art) || size == null || "".equals(size)) {
            response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPRODUCT)
                    .addParam(ART_PARAM, art)
                    .addParam(SHOW_PARAM, YES_VALUE)
                    .addParam(ADDED_PARAM, NO_SIZE_VALUE)
                    .createPath());
            return;
        }

        Cookie[] cookies = request.getCookies();
        boolean isAdded = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ((ART_PREFIX + art).equals(cookie.getName())) {
                    if (cookie.getValue().contains(size)) {
                        isAdded = true;
                    } else {
                        size = cookie.getValue() + COOKIE_DELIMITER + size;
                    }
                }
            }
        }

        Cookie cookie = new Cookie(ART_PREFIX + art, size);
        cookie.setMaxAge(MAX_AGE);
        response.addCookie(cookie);
        if (isAdded) {
            response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPRODUCT)
                    .addParam(ART_PARAM, art)
                    .addParam(SHOW_PARAM, YES_VALUE)
                    .addParam(ADDED_PARAM, YES_VALUE)
                    .createPath());
        } else {
            response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPRODUCT)
                    .addParam(ART_PARAM, art)
                    .addParam(SHOW_PARAM, YES_VALUE)
                    .addParam(ADDED_PARAM, NO_VALUE)
                    .createPath());
        }

    }
}