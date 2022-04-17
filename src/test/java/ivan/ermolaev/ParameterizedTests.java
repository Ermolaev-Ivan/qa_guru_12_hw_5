package ivan.ermolaev;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ParameterizedTests {

    @BeforeAll
    static void beforeAll() {
        System.out.println("@BeforeAll запускается один раз за прогон тестов");
    }

    @BeforeEach
    void beforeEach() {
        open("https://habr.ru");
        System.out.println("@BeforeEach запускается перед каждым тестом");
    }

    static Stream<Arguments> annotationMethodSource() {
        return Stream.of(
                Arguments.of("Selenide, Автоматизация тестирования с использованием Selenide через Selenoid в Docker контейнере"),
                Arguments.of("JUnit", "JUnit, Параллельное тестирование с JUnit 5 и Selenium [Учебное пособие]")
        );
    }

    @ValueSource(strings = {
            "Selenide",
            "JUnit5"})
    @ParameterizedTest(name = "Тестирование поиска хабра при помощи аннотации \"ValueSource\" с тестовыми данными: {0}")
    void habrSearchValueTest(String testData) {
        $(".tm-header-user-menu__search").click();
        $(".tm-input-text-decorated__input").setValue(testData).pressEnter();
        $(".tm-articles-list__item h2 span").shouldHave(Condition.text(testData));
    }

    @CsvSource(value = {
            "Selenide, Автоматизация тестирования с использованием Selenide через Selenoid в Docker контейнере",
            "JUnit, Параллельное тестирование с JUnit 5 и Selenium [Учебное пособие]"
    })
    @ParameterizedTest(name = "Тестирование поиска хабра при помощи аннотации \"CsvSource\" с тестовыми данными: {0}")
    void habrSearchCsvSourceTest(String testData, String expected) {
        $(".tm-header-user-menu__search").click();
        $(".tm-input-text-decorated__input").setValue(testData).pressEnter();
        $(".tm-articles-list__item h2 span").shouldHave(Condition.text(expected));
    }

    @MethodSource("annotationMethodSource()")
    @ParameterizedTest(name = "Тестирование поиска хабра при помощи аннотации \"MethodSource\" с тестовыми данными: {0}")
    void habrSearchMethodSourceTest(String testData, String expected) {
        $(".tm-header-user-menu__search").click();
        $(".tm-input-text-decorated__input").setValue(testData).pressEnter();
        $(".tm-articles-list__item h2 span").shouldHave(Condition.text(expected));
    }
}
