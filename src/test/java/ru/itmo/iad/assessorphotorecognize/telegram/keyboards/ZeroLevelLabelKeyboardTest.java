package ru.itmo.iad.assessorphotorecognize.telegram.keyboards;

import org.junit.jupiter.api.Test;
import ru.itmo.iad.assessorphotorecognize.domain.ZeroLevelLabel;

import static org.junit.jupiter.api.Assertions.*;

class ZeroLevelLabelKeyboardTest {

    private final ZeroLevelLabelKeyboard keyboard = new ZeroLevelLabelKeyboard();

    @Test
    void testGettingKeyboard() {
        var result = keyboard.getKeyboard("123", true);

        assertAll(
                () -> assertEquals(4, result.getKeyboard().size()),
                () -> assertEquals("Внутри помещения", result.getKeyboard().get(0).get(0).getText()),
                () -> assertEquals("Снаружи, нерукотворное", result.getKeyboard().get(1).get(0).getText()),
                () -> assertEquals("Снаружи, рукотворное", result.getKeyboard().get(2).get(0).getText()),
                () -> assertEquals("Пропустить", result.getKeyboard().get(3).get(0).getText())
        );
    }

    @Test
    void testFalseHoneyPot() {
        var result = keyboard.getKeyboard("123", false);
        assertEquals("zero_level_label 123 indoor false", result.getKeyboard().get(0).get(0).getCallbackData());
    }

}