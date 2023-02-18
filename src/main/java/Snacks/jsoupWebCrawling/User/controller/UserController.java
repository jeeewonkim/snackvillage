package Snacks.jsoupWebCrawling.User.controller;

import Snacks.jsoupWebCrawling.Repository.UserRepository;
import Snacks.jsoupWebCrawling.User.Dto.FindUserIdDto;
import Snacks.jsoupWebCrawling.User.Dto.MailDto;
import Snacks.jsoupWebCrawling.User.Dto.MailUserDto;
import Snacks.jsoupWebCrawling.User.Dto.UserSignUpDto;
import Snacks.jsoupWebCrawling.User.Entity.User;
import Snacks.jsoupWebCrawling.User.Service.PrincipalDetailsService;
import Snacks.jsoupWebCrawling.User.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private  final PrincipalDetailsService principalDetailsService;



    public UserController(UserService userService, PasswordEncoder passwordEncoder, PrincipalDetailsService principalDetailsService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.principalDetailsService = principalDetailsService;
    }

    @PostMapping("/auth/signup")
    public ResponseEntity signUp(@Valid @RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/auth/checkId")
    public ResponseEntity checkDuplicated(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.checkDuplicated(userSignUpDto);
        return new ResponseEntity(HttpStatus.OK);
    }


   @PostMapping("/login")
    public ResponseEntity<UserDetails> login(HttpServletRequest request, @RequestBody PrincipalDetails principalDetails) throws Exception
   {
       HttpSession httpSession = request.getSession();
       System.out.println(principalDetails.getUserId());
       System.out.println(httpSession);
       return ResponseEntity.ok(userService.login(principalDetails));
   }


/*   @PostMapping("/authentication")
    public ResponseEntity<Authentication> authentication(@RequestBody UserSignUpDto userSignUpDto){
        return ResponseEntity.ok(customAuthenticationProvider.authenticate(userSignUpDto);
   }*/
}
