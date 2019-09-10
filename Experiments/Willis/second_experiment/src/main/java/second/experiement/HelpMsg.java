package second.experiement;

import org.springframework.stereotype.Service;

@Service
public class HelpMsg
{

  public String helpMsg()
  {
    return "Hello there! You have found the help page. Please return to main page by removing \"/help\" from the URL.";
  }

}
