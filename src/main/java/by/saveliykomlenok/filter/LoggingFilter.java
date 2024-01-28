package by.saveliykomlenok.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.util.Arrays;

@WebFilter("/*")
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.getParameterMap().forEach((t, v) -> System.out.println(t + ":" + Arrays.toString(v)));
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
