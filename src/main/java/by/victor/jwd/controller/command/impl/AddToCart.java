package by.victor.jwd.controller.command.impl;

import by.victor.jwd.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddToCart implements Command {

    private static final String ART_PARAM = "art";
    private static final String SIZE_PARAM = "size";
    private static final String COOKIE_DELIMITER = "|";
    private static final String GOTOPRODUCT_ART = "Controller?command=gotoproduct&art=";
    private static final String ART_PREFIX = "art_";
    private static final int MAX_AGE = 10 * 60;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String art = request.getParameter(ART_PARAM);
        String size = request.getParameter(SIZE_PARAM);
        if (art == null || "".equals(art) || size == null || "".equals(size)) {
            response.sendRedirect(GOTOPRODUCT_ART + art + "&show=yes&added=no_size");
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
            response.sendRedirect(GOTOPRODUCT_ART + art + "&show=yes&added=yes");
        } else {
            response.sendRedirect(GOTOPRODUCT_ART + art + "&show=yes&added=no");
        }

    }
}
