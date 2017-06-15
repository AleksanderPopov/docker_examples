import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.Test;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

/**
 * Created by apop on 5/22/2017.
 */
public class ParallelJunitTest {

  @Test
  public void selenideTest() {
    Class[] cls = {GoogleSearchTest.class, GoogleSearchTest.class, GoogleSearchTest.class, GoogleSearchTest.class};
    JUnitCore.runClasses(new ParallelComputer(true, true), cls);
  }

  public static class GoogleSearchTest {

    private SelenideElement search = $("#lst-ib");
    private ElementsCollection results = $$(".g .r");

    @Test
    public void searchAndFollowFirstResultTest() throws MalformedURLException {

//        DesiredCapabilities ie11 = DesiredCapabilities.internetExplorer();
      Configuration.fastSetValue = true;
      DesiredCapabilities chrome = DesiredCapabilities.chrome();
      chrome.setBrowserName("wchrome");
      chrome.setVersion("59.0");
//        DesiredCapabilities firefox = DesiredCapabilities.firefox();
      System.out.println("browser going to start");
	  WebDriverRunner.setWebDriver(new RemoteWebDriver(new URL("http://test:test-password@172.31.29.77:4444/wd/hub"), chrome));
		System.out.println("browser started");
      open("http://google.com/ncr");
      search.setValue("Selenium automates browsers").pressEnter();
      results.filter(visible).shouldHaveSize(10)
              .first()
              .shouldHave(text("Selenium - Web Browser Automation"))
              .find("a")
              .click();
      assertUrl("http://www.seleniumhq.org/");
      System.out.println(getWebDriver().getTitle());

      getWebDriver().quit();
    }

    private void assertUrl(String url) {
      new WebDriverWait(getWebDriver(), 5).until(urlToBe(url));
    }

  }
}