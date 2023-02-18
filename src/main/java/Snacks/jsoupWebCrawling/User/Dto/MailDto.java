package Snacks.jsoupWebCrawling.User.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MailDto {
    private String address;
    private String title;
    private String message;


    @Builder
    public MailDto(String address, String title, String message){
        this.address = address;
        this.title = title;
        this.message = message;

    }

}


