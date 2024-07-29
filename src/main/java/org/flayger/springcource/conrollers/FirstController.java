package org.flayger.springcource.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/first")
public class FirstController {

    @GetMapping("/hello")
    public String helloPage(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "surname", required = false) String surname,
            Model model
    ) {

        //System.out.println();
        model.addAttribute("message", name + surname + " got");
        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodbyePage() {
        //System.out.println(name + surname + " got");
        return "first/goodbye";
    }

    @GetMapping("/calculator")
    public String doCalc(
            @RequestParam(value = "a", required = false) Integer a,
            @RequestParam(value = "b", required = false) Integer b,
            @RequestParam(value = "action", required = false) String action,
            Model model

    ) {
        double result = 0;
        if (a != null && b != null && action != null) {
            model.addAttribute("a", a);
            model.addAttribute("b", b);
            model.addAttribute("action", action);

            switch (action) {
                case "multiplication":
                    result = a * b;
                    break;
                case "addition":
                    result = a + b;
                    break;
                case "subtraction":
                    result = a - b;
                    break;
                case "division":
                    result = a / (double) b;
                    break;
                default:
                    model.addAttribute("error", "wrong action");
                    break;
            }
        }
        model.addAttribute("result", result);
        return "first/calculator";
    }

}
