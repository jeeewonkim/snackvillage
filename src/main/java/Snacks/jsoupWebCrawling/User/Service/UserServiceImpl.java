package Snacks.jsoupWebCrawling.User.Service;

import Snacks.jsoupWebCrawling.Repository.UserRepository;
import Snacks.jsoupWebCrawling.User.Dto.FindUserIdDto;
import Snacks.jsoupWebCrawling.User.Dto.MailDto;
import Snacks.jsoupWebCrawling.User.Dto.MailUserDto;
import Snacks.jsoupWebCrawling.User.Dto.UserSignUpDto;
import Snacks.jsoupWebCrawling.User.Entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final PrincipalDetailsService principalDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender emailSender;


    /*회원가입*/
    public void signUp(UserSignUpDto userSignUpDto) throws Exception {
        User user = userSignUpDto.toEntity();
        user.encodePassword(passwordEncoder);
        userRepository.save(user);
    }

    /*아이디 중복체크*/
    public void checkDuplicated(UserSignUpDto userSignUpDto) throws Exception {
        User userCheck = userRepository.findByUserId(userSignUpDto.getUserId());
        if (userCheck != null) {
            throw new Exception("ID exists");
        }
    }

    /*이메일 생성,후 변경된 비밀번호로 업데이트*/
    public MailDto createMailAndChangePassword(MailUserDto mailUserDto) throws Exception {
        String newPassword = getTempPassword();
        String userEmail = userRepository.findByUserId(mailUserDto.getUserId()).getUserEmail();
        String mail = mailUserDto.getUserEmail();
        log.info("createMailAndChangePassword new password = {}", newPassword);
        log.info("createMail And changePassword userEmail = {}", userEmail.getClass().getName());
        log.info("createMail And changePassword mailUserDto = {}", mailUserDto.getUserEmail().getClass().getName());
        if (!mail.equals(userEmail)) {
            throw new Exception("이메일과 아이디가 일치하지 않습니다");
        }

        MailDto mailDto = new MailDto().builder()
                .address(userEmail)
                .title("스낵 빌리지 임시비밀번호 안내 이메일 입니다.")
                .message("스낵 빌리지 임시 비밀번호 " + newPassword)
                .build();

        updatePassword(newPassword, mailUserDto.getUserId());

        return mailDto;
    }

    /*비밀번호 DB 업데이트*/
    public void updatePassword(String newPassword, String userId) {
        User user = userRepository.findByUserId(userId);

        log.info("변경 전 패스워드 = {}", user.getPassword());

        user.setPassword(newPassword);
        user.encodePassword(passwordEncoder);
        log.info("변경후 패스워드 = {}", user.getPassword());
    }

    /*랜덤비밀번호 생성*/
    public String getTempPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    /*메일 전송*/
    public void mailSend(MailDto mailDto) {
        System.out.println("전송 완료!");
        String emailTo = mailDto.getAddress();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        log.info("mailDto.getAddress ={}", mailDto.getAddress());

        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        message.setFrom("wldnjs3865@naver.com");
        message.setReplyTo("wldnjs3865@naver.com");
        System.out.println("message" + message);

        emailSender.send(message);
    }

    /*userName, userEmail로 아이디 찾기*/
    // 소셜 로그인은 userEmail(o) username(o) userId(x)
    // 일반 로그인은 userEmail(o) userName(o) userId(o)
    public String findUserId(FindUserIdDto findUserIdDto) throws Exception {
        String userName = userRepository.findByUserEmail(findUserIdDto.getUserEmail()).getUserName();
        String userId = userRepository.findByUserEmail(findUserIdDto.getUserEmail()).getUserId();

        log.info("findUserId name={}", userName);
        log.info("findUserId userId={}", userId);

        if (userName == findUserIdDto.getUserName()) { //입력한 이름과, 이메일에 따른 이름이 같을때
            log.info("userName 일치");
            if (userId == null) {
                throw new Exception("해당 사용자가 존재하지 않습니다.");
            }
        }
        return userId;
    }
}



