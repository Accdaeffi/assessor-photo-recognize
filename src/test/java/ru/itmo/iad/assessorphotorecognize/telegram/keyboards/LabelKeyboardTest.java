package ru.itmo.iad.assessorphotorecognize.telegram.keyboards;

import org.junit.jupiter.api.Test;
import ru.itmo.iad.assessorphotorecognize.domain.ZeroLevelLabel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LabelKeyboardTest {

    private final LabelKeyboard keyboard = new LabelKeyboard();

    @Test
    void testIndoor() {
        var result = keyboard.getKeyboard("123", ZeroLevelLabel.INDOOR, true);

        assertAll(
                () -> assertEquals(8, result.getKeyboard().size()),
                () -> assertEquals("Коммерция и общепит", result.getKeyboard().get(0).get(0).getText()),
                () -> assertEquals("Место работы", result.getKeyboard().get(1).get(0).getText()),
                () -> assertEquals("Жилое помещение", result.getKeyboard().get(2).get(0).getText()),
                () -> assertEquals("Транспорт", result.getKeyboard().get(3).get(0).getText()),
                () -> assertEquals("Спорт и развлечения", result.getKeyboard().get(4).get(0).getText()),
                () -> assertEquals("Культура, образование, государство", result.getKeyboard().get(5).get(0).getText()),
                () -> assertEquals("Неоднозначное", result.getKeyboard().get(6).get(0).getText()),
                () -> assertEquals("Назад", result.getKeyboard().get(7).get(0).getText())
        );
    }

    @Test
    void testManMade() {
        var result = keyboard.getKeyboard("123", ZeroLevelLabel.OUTDOOR_MAN_MADE, true);

        assertAll(
                () -> assertEquals(7, result.getKeyboard().size()),
                () -> assertEquals("Транспорт", result.getKeyboard().get(0).get(0).getText()),
                () -> assertEquals("Культурное, историческое, военное", result.getKeyboard().get(1).get(0).getText()),
                () -> assertEquals("Спорт, развлечения и парки", result.getKeyboard().get(2).get(0).getText()),
                () -> assertEquals("Промышленность", result.getKeyboard().get(3).get(0).getText()),
                () -> assertEquals("Загородные объекты", result.getKeyboard().get(4).get(0).getText()),
                () -> assertEquals("Городские объекты", result.getKeyboard().get(5).get(0).getText()),
                () -> assertEquals("Назад", result.getKeyboard().get(6).get(0).getText())
        );
    }

    @Test
    void testNatural() {
        var result = keyboard.getKeyboard("123", ZeroLevelLabel.OUTDOOR_NATURAL, true);

        assertAll(
                () -> assertEquals(4, result.getKeyboard().size()),
                () -> assertEquals("Вода", result.getKeyboard().get(0).get(0).getText()),
                () -> assertEquals("Рельеф или пустыни", result.getKeyboard().get(1).get(0).getText()),
                () -> assertEquals("Леса и поля", result.getKeyboard().get(2).get(0).getText()),
                () -> assertEquals("Назад", result.getKeyboard().get(3).get(0).getText())
        );
    }

    @Test
    void testFalseHoneyPot() {
        var result = keyboard.getKeyboard("123", ZeroLevelLabel.OUTDOOR_NATURAL, false);
        assertEquals("label 123 water false", result.getKeyboard().get(0).get(0).getCallbackData());
    }
}