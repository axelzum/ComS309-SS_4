package second.experiement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainPageTest
{

  @Autowired
  private IndexController controller;

  @Test
  public void loadsContext() throws Exception
  {
    assertThat(controller).isNotNull();
  }

  @Test
  public void messageCorrect() throws Exception
  {
    assertEquals("Welcome to the home page", controller.hello());
  }

  @Test
  public void indexNotEqualsHome() throws Exception
  {
    assertNotEquals(controller.hello(), controller.homePage());
  }

}
