package Snacks.jsoupWebCrawling.User;

import Snacks.jsoupWebCrawling.User.Dto.UserSignUpDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional //로직을 처리하다가 에러가 발생하면, 변경된 데이터를 로직을 수행하기 이전 상태로 콜백
@Service
public interface UserService {
    void signUp(UserSignUpDto userSignUpDto) throws Exception;
    UserDetails checkDuplicated(UserSignUpDto userSignUpDto) throws Exception;
    //UserDetails login(PrincipalDetails principalDetails) throws Exception;

    //Authentication login(Authentication authentication);

}