package Snacks.jsoupWebCrawling.User.Security;

import Snacks.jsoupWebCrawling.User.Dto.LoginDto;
import Snacks.jsoupWebCrawling.User.Service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CookieValue;


import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.UUID;

/*사용자의 아이디와 패스워드를 인터셉트
* UsernamePasswordAuthenticationToken 전달받음*/
@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    private static final String DEFAULT_LOGIN_REQUEST_URL = "/auth/login";
    private static final String HTTP_METHOD = "POST";
    private static final String CONTENT_TYPE = "application/json";
    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD);
    private final ObjectMapper objectMapper;

    private final UserServiceImpl userService;

    protected CustomAuthenticationFilter(ObjectMapper objectMapper,
                                         AuthenticationFailureHandler authenticationFailureHandler,
                                         AuthenticationSuccessHandler authenticationSuccessHandler, UserServiceImpl userService) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);
        this.objectMapper = objectMapper;
        this.userService = userService;
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(authenticationFailureHandler);

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        log.info("AuthenticationFilter Run");

        if (request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)) {
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType()) {
            };
        }
        String json = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        log.info("json = {}", json);

        LoginDto loginDto = objectMapper.readValue(json, LoginDto.class);

        System.out.println(loginDto);
        String userId = loginDto.getUserId();
        String password = loginDto.getPassword();
        boolean remember = loginDto.isRemember();

        if (userId == null || password == null) {
            throw new AuthenticationServiceException("DATA IS MISS");
        }
        log.info("At filter boolean remember ={}", remember);

        ResponseCookie cookie = userService.autoLogin(remember, response);
        response.addHeader("rememberCookie", cookie.toString());

        log.info("At filter cookie = {}", cookie);
        log.info("At filter response cookie = {}", cookie);
//        log.info("At filter cookie name = {}", cookie.getName());


        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userId, password);

        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }


}
