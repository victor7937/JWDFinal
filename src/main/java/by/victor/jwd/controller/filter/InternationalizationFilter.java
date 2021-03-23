package by.victor.jwd.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebFilter(filterName = "InternationalizationFilter", urlPatterns = "/*")
public class InternationalizationFilter implements Filter {
    private static final String LANG_ATTRIBUTE = "lang";

    public void init(FilterConfig config) throws ServletException {}

    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

       HttpSession session = ((HttpServletRequest)request).getSession();
        if (session.getAttribute(LANG_ATTRIBUTE) == null) {
            session.setAttribute(LANG_ATTRIBUTE, request.getLocale().getLanguage());
        }
        String langParam = request.getParameter(LANG_ATTRIBUTE);
        if (langParam != null) {
            session.setAttribute(LANG_ATTRIBUTE, langParam);
        }

        chain.doFilter(request, response);
    }
}
