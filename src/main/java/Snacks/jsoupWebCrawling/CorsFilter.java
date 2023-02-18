package Snacks.jsoupWebCrawling;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Configuration
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)//(없으면 실행 안됨)
public class CorsFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("Reached Cors Filter");

        HttpServletResponse response2 = (HttpServletResponse) response;
        response2.setHeader("Access-Control-Allow-Origin", "*");
        response2.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response2.setHeader("Access-Control-Max-Age", "3600");
        response2.setHeader("Access-Control-Allow-Headers",
                "Origin, Content-Type, Accept, Authorization");
        response2.setHeader("Access-Control-Allow-Credentials", "true");

        chain.doFilter(request, response);
        logger.info("CORS FILTER set");
    }
}
