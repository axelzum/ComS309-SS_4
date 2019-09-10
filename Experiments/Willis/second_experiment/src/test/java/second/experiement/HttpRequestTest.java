package second.experiement;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest
{
  // checks to see if the HTTP request worked on all three pages

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void retMsgNoIndex() throws Exception
  {
    assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class))
            .contains("Welcome to the home page");
  }

  @Test
  public void retMsgIndex() throws Exception
  {
    assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/index", String.class))
            .contains("This is the index page");
  }

  @Test
  public void retHelpMsg() throws Exception
  {
    assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/help", String.class))
            .contains("Hello there! You have found the help page. Please return to main page by removing \"/help\" from the URL.");
  }


}
