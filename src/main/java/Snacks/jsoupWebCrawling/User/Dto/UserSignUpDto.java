package Snacks.jsoupWebCrawling.User.Dto;

import Snacks.jsoupWebCrawling.User.User;

public class UserSignUpDto {
    private String userId;
    private String userName;
    private String password;

    private String userEmail;

    public String getUserId() {
        return userId;
    }

    public UserSignUpDto(String userId, String userName, String password , String userEmail){
        this.userId = userId;
        this.userName  = userName;
        this.password = password;
        this.userEmail = userEmail;
    }
    public User toEntity(){
        return User.builder()
                .userId(userId)
                .userName(userName)
                .userEmail(userEmail)
                .role("ROLE_USER")
                .password(password)
                .platformType("own")
                .build();
    }

}
