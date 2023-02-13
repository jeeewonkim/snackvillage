package Snacks.jsoupWebCrawling.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*사용자의 아이디와 패스워드를 인터셉트
* UsernamePasswordAuthenticationToken 전달받음*/
//@Component
@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    protected CustomAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        log.info("CustomAuthenticationFilter userId ={}" , userId );
        log.info("CustomAuthenticationFilter password ={}" , password );

        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(userId, password));
    }
}
