package by.victor.jwd.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class InternationalizationFilter implements Filter {
    private static final String LANG_ATTRIBUTE = "lang";
    private static final ResourceBundle bundle = ResourceBundle.getBundle("application");
    private static final String ALL_LANGUAGES = bundle.getString("languages");
    public static final String DELIMITER = ",";

    public void init(FilterConfig config) throws ServletException {}

    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

       HttpSession session = ((HttpServletRequest)request).getSession();
        if (session.getAttribute(LANG_ATTRIBUTE) == null) {
            session.setAttribute(LANG_ATTRIBUTE, langChecking(request.getLocale().getLanguage()));
        }
        String langParam = request.getParameter(LANG_ATTRIBUTE);

        if (langParam != null && !Objects.equals(session.getAttribute(LANG_ATTRIBUTE), langParam)) {
            session.setAttribute(LANG_ATTRIBUTE, langChecking(langParam));
        }

        chain.doFilter(request, response);
    }

    private static String langChecking (String lang) {
        String[] languages = ALL_LANGUAGES.split(DELIMITER);
        if (Arrays.stream(languages).noneMatch(lang::equals)) {
            lang = languages[0];
        }
        return lang;
    }
}
