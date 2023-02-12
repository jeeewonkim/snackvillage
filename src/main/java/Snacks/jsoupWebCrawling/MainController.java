package Snacks.jsoupWebCrawling;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class MainController {
    @GetMapping(value ="/")
    public String main(){
        return "main";
    }
}
