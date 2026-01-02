package com.binewsian.controller;

import com.binewsian.annotation.RequireRole;
import com.binewsian.enums.Role;
import com.binewsian.model.News;
import com.binewsian.model.User;
import com.binewsian.service.NewsService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final NewsService newsService;

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    @RequireRole({Role.USER, Role.CONTRIBUTOR, Role.ADMIN})
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<News> news = newsService.findLatestPublished();

        model.addAttribute("user", user);
        model.addAttribute("news",  news);

        return "dashboard";
    }

}
