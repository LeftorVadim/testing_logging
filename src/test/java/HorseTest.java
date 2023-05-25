import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HorseTest {
    @Test()
    void NameOfHorseNull() {
        Throwable throwable = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(null, 2.5, 5.5));
        Assertions.assertEquals(throwable.getMessage(), "Name cannot be null.");
    }

    @ParameterizedTest
    @CsvSource({"\t*"})
    void NameOfHorseIsBlankOrSpace() {
        Throwable throwable = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse("   ", 2.2, 5.5));
        Assertions.assertEquals(throwable.getMessage(), "Name cannot be blank.");
    }

    @Test
    void checkThatTheSecondArgumentIsNotNegative() {
        Throwable throwable = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse("Пегас", -1.0, 5.4));
        Assertions.assertEquals(throwable.getMessage(), "Speed cannot be negative.");
    }

    @Test
    void checkThatTheThirdArgumentIsNotNegative() {
        Throwable throwable = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse("Пегас", 1.0, -5.4));
        Assertions.assertEquals(throwable.getMessage(), "Distance cannot be negative.");
    }

    @Test
    void checkTheNameWhichShouldBeTheFirstParameter() {
        Horse horse = new Horse("Вишня", 2.3, 5.4);
        String name = horse.getName();
        Assertions.assertEquals("Вишня", name);
    }

    @Test
    void checkTheSpeedWhichShouldBeTheFirstParameter() {
        Horse horse = new Horse("Вишня", 2.3, 5.4);
        Double speed = horse.getSpeed();
        Assertions.assertTrue(2.3 == speed);
    }

    @Test
    void checkTheDistanceWhichShouldBeThirdParameter() {
        Horse horse = new Horse("Вишня", 2.3, 5.4);
        Double distance = horse.getDistance();
        Assertions.assertTrue(5.4 == distance);
    }

    @Test
    void checkTheDistanceWhichCanBeNull() {
        Horse horse = new Horse("Пегас", 5.0);
        Double distance = horse.getDistance();
        Assertions.assertTrue(0 == distance);
    }

    @Test
    void checkArgumentsOfGetRandomDoubleMethod() {
        try (MockedStatic<Horse> mock = Mockito.mockStatic(Horse.class)) {
            new Horse("Пегас", 4.5).move();
            mock.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.3, 1.0, 0.0, 0.7})
    void checkThatTheMethodCorrectlyAssignsValue(double random) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Зефир", 2.0, 2.3);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            horse.move();
            Assertions.assertEquals(2.3 + 2.0 * random, horse.getDistance());
        }
    }

}