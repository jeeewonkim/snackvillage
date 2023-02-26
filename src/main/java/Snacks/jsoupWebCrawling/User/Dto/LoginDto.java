package Snacks.jsoupWebCrawling.User.Dto;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class LoginDto {
    String userId;
    String password;
    boolean remember;
}
