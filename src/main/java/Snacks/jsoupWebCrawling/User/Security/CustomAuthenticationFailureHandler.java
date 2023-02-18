package Snacks.jsoupWebCrawling.User.Security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(exception instanceof BadCredentialsException){
            log.error("비밀번호 잘못입력함"); }
        if(exception instanceof UsernameNotFoundException)
            log.error("게정 존재하지 않음");

        log.info("login_Failue");
        new ResponseEntity(HttpStatus.UNAUTHORIZED);
       //response.sendRedirect("http://localhost:8080/loginFail");
    }
}
