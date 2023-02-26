package Snacks.jsoupWebCrawling.User.Security;

import Snacks.jsoupWebCrawling.Repository.UserRepository;
import Snacks.jsoupWebCrawling.User.Dto.LoginDto;
import Snacks.jsoupWebCrawling.User.Service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("SuccessHandler Run");
        HttpSession httpSession = request.getSession(true);
        httpSession.setMaxInactiveInterval(60);
        log.info("get session true? = {} ", httpSession.isNew());
        httpSession.setAttribute("userId", authentication.getName());

        log.info("Attribute = {}", httpSession.getAttribute("userId"));
        log.info("get session Id = {}", httpSession.getId());
      //  findCookie(request);
        Cookie[] autoCookie = request.getCookies();
        log.info("autoCookie = {}", autoCookie);
    }

    public void findCookie(HttpServletRequest request) { //,
        Cookie[] cookies = request.getCookies();
        log.info("findcookies = {}", cookies);
        System.out.println(Arrays.stream(cookies)
                .filter(cookie1 -> cookie1.getName().equals("rememberCookie"))
                .findAny().orElse(null).getMaxAge());
           /*     if (cookies == null) {
            return null;
        }
*/
       /* return ResponseEntity.ok(Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("rememberCookie"))
                .findAny().orElse(null));
*/
        // log.info("successHandler maxage = {}", cookie.getMaxAge());
    }

}