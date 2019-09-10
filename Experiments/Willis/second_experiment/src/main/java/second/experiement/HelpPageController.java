package second.experiement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelpPageController
{

  private final HelpMsg msg;

  public HelpPageController(HelpMsg helpMsg)
  {
    msg = helpMsg;
  }

  @RequestMapping("/help")
  public @ResponseBody String display()
  {
    return msg.helpMsg();
  }

}
