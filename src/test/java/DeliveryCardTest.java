import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {

    public String chooseDate(int Date) {
        return LocalDate.now().plusDays(Date).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldTestCard() {
        String set = chooseDate(10);
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[placeholder=\"Город\"]").setValue("Волгоград");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder=\"Дата встречи\"]").setValue(set);
        $("[name=\"name\"]").setValue("Брен Саша");
        $("[name=\"phone\"]").setValue("+79191234567");
        $("[data-test-id=\"agreement\"]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $(withText("Успешно!")).should(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + set));
        Configuration.holdBrowserOpen = false;
    }
}