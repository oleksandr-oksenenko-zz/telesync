package net.telesync.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomePageController {

    @RequestMapping(method = RequestMethod.GET)
    public String showHomePage() {
        return "index";
    }

}
