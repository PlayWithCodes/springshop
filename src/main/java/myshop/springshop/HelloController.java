package myshop.springshop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("welcome")
    public String greeting(Model model) {
        model.addAttribute("greeting", "hello~");
        return "welcome";
    }
}
