package com.binewsian.controller.user;

import com.binewsian.annotation.RequireRole;
import com.binewsian.enums.Role;
import com.binewsian.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequireRole({Role.USER, Role.CONTRIBUTOR, Role.ADMIN})
public class UserController {

    @GetMapping("/profile")
    public String showUserProfilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "user/profile";
    }

}
