package by.victor.jwd.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

//@WebFilter(filterName = "EncodingFilter", urlPatterns = "/*")
public class EncodingFilter implements Filter {

    private final static String ENCODING = "UTF-8";

    public void init(FilterConfig config) throws ServletException { }

    public void destroy() { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        if(!ENCODING.equals(request.getCharacterEncoding())) {
            request.setCharacterEncoding(ENCODING);
        }
        if (!ENCODING.equals(response.getCharacterEncoding())){
            response.setCharacterEncoding(ENCODING);
        }
        chain.doFilter(request, response);
    }
}
