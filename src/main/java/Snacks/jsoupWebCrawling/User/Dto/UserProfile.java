package Snacks.jsoupWebCrawling.User.Dto;

import Snacks.jsoupWebCrawling.User.Entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProfile {
    private String userName;
    private String userEmail;
    private String platformType;

    public User toUser(){
        return User.builder()
                .userName(userName)
                .userEmail(userEmail)
                .platformType(platformType)
                .build();
    }
}
