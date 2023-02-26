package Snacks.jsoupWebCrawling.User.Service;

import Snacks.jsoupWebCrawling.Repository.UserRepository;
import Snacks.jsoupWebCrawling.User.Entity.PrincipalDetails;
import Snacks.jsoupWebCrawling.User.Entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public PrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId);
        log.info("PrincipalDetailsService Run");
        log.info("principalDetailsService userId ={}", userId);
        log.info("principalDetailsService user = {}", user);
        if(user == null) {
            log.error("해당 사용자가 존재하지 않음");
            throw new UsernameNotFoundException(userId);
        }
        log.info("사용자 존재");
        return new PrincipalDetails(userRepository.findByUserId(userId));
    }
}
