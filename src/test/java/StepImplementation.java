import com.thoughtworks.gauge.Step;
import io.appium.java_client.MobileElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import java.lang.reflect.Method;
import java.util.Random;

public class StepImplementation extends BaseTest {
    Random rand = new Random();
    Logger logger = LogManager.getLogger(StepImplementation.class);
    String gidisSaati;
    String inisSaati;

    @Step("<id> 'li id element görünüyor mu")
    public void isDisplay(String id) {
        MobileElement element = appiumDriver.findElement(By.id(id));
        Assert.assertTrue(element.getText() + " elementi görünmüyor", element.isDisplayed() == true);
        logger.info("Uygulama Acildi.");

    }

    @Step("<x> saniye kadar bekle")
    public void waitForSecods(int x) throws InterruptedException {
        Thread.sleep(1000 * x);
    }

    @Step("Tanıtım ekranını geç")
    public void SkipCokkie() throws InterruptedException {
        MobileElement element = appiumDriver.findElement(By.id("com.m.qr:id/skip_button"));
        if (element.isDisplayed()) {
            logger.info("Tanitim ekranlari gecildi");
            element.click();
            appiumDriver.findElement(By.id("com.m.qr:id/onboarding_skip_button")).click();
            System.out.println("Notification Bekleniyor");
            Thread.sleep(10000);
            appiumDriver.findElement(By.id("com.m.qr:id/push_consent_decline")).click();
            System.out.println("Decline tıklandı");
        } else {
            System.out.println("Tanim ekranı görünür olmadı");

        }

    }

    @Step("<id> li elemente tıkla")
    public void clickByid(String id) {
        appiumDriver.findElement(By.id(id)).click();
    }

    @Step("<xpath> xpath li elemente tıkla")
    public void clickByxpath(String xpath) {
        appiumDriver.findElement(By.xpath(xpath)).click();
    }

    @Step("<id> li elemente <value> text değerini yaz")
    public void sendKeyId(String id, String value) {
        MobileElement element = appiumDriver.findElement(By.id(id));
        // element.clear();
        element.sendKeys(value);
    }

    @Step("Uçuş seçim ekranı kontrolü")
    public void control() throws InterruptedException {
        MobileElement element = appiumDriver.findElement(By.id("com.m.qr:id/booking_activity_conversational_message"));
        if (element.isDisplayed()) {
            logger.info("Ekran Acildi.");
        } else {
            System.out.println("Ekrana gecilemedi");
        }
    }

    @Step("7 gün sonrasına tarih seç")
    public void plus7Days() {
        new Actions(appiumDriver)
                .sendKeys(Keys.TAB).sendKeys(Keys.TAB)
                .sendKeys(Keys.TAB).sendKeys(Keys.TAB)
                .sendKeys(Keys.TAB).sendKeys(Keys.TAB)
                .sendKeys(Keys.TAB).sendKeys(Keys.TAB)
                .sendKeys(Keys.ENTER).perform();
        logger.info("Takvimden tarih secimi basarili.");

    }

    @Step("<xpath> 'li uçuşlardan rastgele seç")
    public void randomSelectFly(String xpath) {

        int random = rand.nextInt(4);
        if (random == 0) {
            random = random + 1;
        }
        System.out.println("random: " + random);
        MobileElement element = appiumDriver.findElement(By.xpath("(" + xpath + ")[" + random + "]"));
        gidisSaati = appiumDriver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[" + random + "]/*[@resource-id=\"com.m.qr:id/rvmp_departure_time\"][1]")).getText();
        inisSaati = appiumDriver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[" + random + "]/*[@resource-id=\"com.m.qr:id/rvmp_arrival_time\"][1]")).getText();
        element.click();
        logger.info("Herhangi bir ucus basariyla secildi.");
    }

    @Step("Bilet ekran kontrolü")
    public void as() {
      String controlGidis=appiumDriver.findElement(By.id("com.m.qr:id/from_time")).getText();
      String controlInis=appiumDriver.findElement(By.id("com.m.qr:id/to_time")).getText();

      Assert.assertEquals("Uçuş saatlerinin önceki ve sonraki halleri eşit değil!",gidisSaati,controlGidis);
      Assert.assertEquals("Varış saatlerinin önceki ve sonraki halleri eşit değil!",inisSaati,controlInis);
    }
}