package com.fourmakers.mapapp.app;

import com.fourmakers.mapapp.config.auth.LoginUser;
import com.fourmakers.mapapp.config.auth.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionMember member){

        if (member == null){
            return "redirect:/oauth2/authorization/kakao";
        }

        model.addAttribute("member", member.getName());
        return "index";
    }
}
