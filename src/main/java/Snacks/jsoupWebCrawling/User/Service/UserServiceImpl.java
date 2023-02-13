package Snacks.jsoupWebCrawling.User.Service;

import Snacks.jsoupWebCrawling.Repository.UserRepository;
import Snacks.jsoupWebCrawling.User.Dto.UserSignUpDto;
import Snacks.jsoupWebCrawling.User.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    public void checkDuplicated(UserSignUpDto userSignUpDto) throws Exception {
        User userCheck = userRepository.findByUserId(userSignUpDto.getUserId());
        if (userCheck !=null){
            throw new Exception("ID exists");
        }
    }
}

