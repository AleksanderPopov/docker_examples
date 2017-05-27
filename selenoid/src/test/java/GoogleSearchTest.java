import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

/**
 * Created by Alex on 1/15/2017.
 */
public class GoogleSearchTest {

    private SelenideElement search = $("#lst-ib");
    private ElementsCollection results = $$(".g .r");

    @BeforeClass
    public static void baseBeforeClass() throws MalformedURLException {
        Configuration.browser = "chrome";
        Configuration.screenshots = false;
        WebDriverRunner.setWebDriver(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome()));
    }

    @Test
    public void searchAndFollowFirstResultTest() {

        open("http://google.com/ncr");
        search.setValue("Selenium automates browsers").pressEnter();
        results.filter(visible).shouldHaveSize(10)
                .first()
                .shouldHave(text("Selenium - Web Browser Automation"))
                .find("a")
                .click();
        assertUrl("http://www.seleniumhq.org/");

    }

    private void assertUrl(String url) {
        new WebDriverWait(getWebDriver(), 5).until(urlToBe(url));
    }

}