package first.experiment;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController
{
  private static final String nameTemplate = "Hello, %s!";
  private static final String emailTemplate = "Your email is set to: %s.";
  private static final String userNameTemplate = "Your userName is: %s";
  private final AtomicInteger counter = new AtomicInteger();

  @RequestMapping("/welcome")
  public User user(@RequestParam(value = "name", defaultValue = "World") String name,
                   @RequestParam(value = "email", defaultValue = "your_email@gmail.com") String setEmail,
                   @RequestParam(value = "userName", defaultValue = "newUser") String setUserName)
  {
    return new User(counter.incrementAndGet(),
                    String.format(nameTemplate, name), String.format(emailTemplate, setEmail),
                    String.format(userNameTemplate, setUserName));
  }

}

