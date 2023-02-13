package Snacks.jsoupWebCrawling.User.Security;

import Snacks.jsoupWebCrawling.User.Service.PrincipalDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



// manager -> provider -> userdetails -> manager -> securityFIlter -> SecurityContext
@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PrincipalDetailsService principalDetailsService;

    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(PasswordEncoder passwordEncoder, PrincipalDetailsService principalDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.principalDetailsService = principalDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userId = authentication.getName();
        String password = (String) authentication.getCredentials();


        UserDetails user = principalDetailsService.loadUserByUsername(userId);

        if(!this.passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("패스워드가 정확하지 않습니다.");
        } else

        return new UsernamePasswordAuthenticationToken(userId, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
