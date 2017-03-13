package Filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clanner on 2017/3/9.
 */
@Component
public class CorsFilter implements Filter {

    private List<String> list = new ArrayList<String>();

    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
        list.add("http://localhost:3000");
        list.add("http://119.29.77.37");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("doFilter");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String origin = (String) servletRequest.getLocalName() + servletRequest.getLocalPort();

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(servletRequest, response);
    }

    public void destroy() {
        System.out.println("destroy");
    }
}
