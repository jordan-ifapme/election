package be.ifapme.election.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("index")
    public String index(Model model) {
        model.addAttribute("message", "Bienvenue sur élections");
        return "index";
    }
}
