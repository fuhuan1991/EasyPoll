package com.polling;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RestController
//@RequestMapping("api/users")
public class PageController {

  // initial configuration page
  @GetMapping("/admin")
  public String admin() {
    return "admin";
  }

}


//public class PageController {
//
//  @GetMapping("/index")
//  public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
//    model.addAttribute("name", name);
//    return "greeting";
//  }
//
//}