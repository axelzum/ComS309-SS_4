package second.experiement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController
{

  @RequestMapping("/")
  public @ResponseBody String hello()
  {
    return "Welcome to the home page";
  }

  @RequestMapping("/index")
  public @ResponseBody String homePage()
  {
    return "This is the index page"; //should call other method
  }

}
