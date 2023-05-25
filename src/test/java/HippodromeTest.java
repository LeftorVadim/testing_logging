import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {
    @Test()
    void parameterOfConstructorCannotBeNull() {
        Throwable throwable = Assertions.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        Assertions.assertEquals(throwable.getMessage(), "Horses cannot be null.");
    }

    @Test
    void parameterOfConstructorCannotBeEmpty() {
        Throwable throwable = Assertions.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        Assertions.assertEquals(throwable.getMessage(), "Horses cannot be empty.");
    }

    @Test
    void getHorsesTest() {
        List<Horse> horses = new ArrayList<>();

        for (int i = 1; i < 31; i++) {
            horses.add(new Horse("Horse number " + i, i));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        Assertions.assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void moveTest() {
        List<Horse> horses = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            Horse mock = Mockito.mock(Horse.class);
            horses.add(mock);
        }

        new Hippodrome(horses).move();

        for (Horse horse : horses) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    void getWinnerTest() {
        Horse horse = new Horse("Пегас", 2.3, 2.0);
        Horse horse1 = new Horse("Пожар", 1.0, 4.5);
        Horse horse2 = new Horse("Буцефал", 2.4, 3.5);
        Hippodrome hippodrome = new Hippodrome(List.of(horse, horse1, horse2));
        Assertions.assertSame(horse1, hippodrome.getWinner());
    }
}