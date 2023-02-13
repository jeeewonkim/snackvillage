package Snacks.jsoupWebCrawling.User;

import Snacks.jsoupWebCrawling.Repository.UserRepository;
import Snacks.jsoupWebCrawling.User.Dto.UserSignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final PrincipalDetailsService principalDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpDto userSignUpDto) throws Exception {
        User user = userSignUpDto.toEntity();
        user.encodePassword(passwordEncoder);
        userRepository.save(user);
    }

    public UserDetails checkDuplicated(UserSignUpDto userSignUpDto) throws Exception {
       /* User userCheck = userRepository.findByUserId(userSignUpDto.getUserId());
        if (userCheck != null) {
            throw new Exception("ID exists");
        }*/
        return principalDetailsService.loadUserByUsername(userSignUpDto.getUserId());
    }

/*    public UserDetails login(PrincipalDetails principalDetails){
        String userId = principalDetails.getUsername();
        String password = principalDetails.getPassword();
        UserDetails userDetails = principalDetailsService.loadUserByUsername(userId);
        if(!this.passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("password is not correct");
        }

        SecurityContextHolder.getContext().setAuthentication();
        return principalDetails;
    }*/
/*    public Authentication login(PrincipalDetails principalDetails) throws AuthenticationException {

    String userId = principalDetails.getUsername();
    String password = (String) principalDetailsService.getCredentials();

    UserDetails user = principalDetailsService.loadUserByUsername(userId);

    if(this.passwordEncoder.matches(password, user.getPassword())){
        throw new BadCredentialsException("패스워드가 정확하지 않습니다.");
    }

    //SecurityContextHolder.getContext().setAuthentication(authentication);
    return new UsernamePasswordAuthenticationToken(userId, password, user.getAuthorities());
}*/

}

