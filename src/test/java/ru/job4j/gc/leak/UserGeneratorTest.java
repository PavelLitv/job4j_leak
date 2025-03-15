package ru.job4j.gc.leak;

import org.junit.jupiter.api.Test;
import ru.job4j.gc.leak.models.User;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserGeneratorTest {
    private final UserGenerator userGenerator = new UserGenerator(new Random());

    @Test
    void whenRandomUserThenNotNull() {
        userGenerator.generate();
        assertThat(userGenerator.randomUser()).isNotNull();
    }

    @Test
    void whenRandomUserThenNameNotEmpty() {
        userGenerator.generate();
        User user = userGenerator.randomUser();
        assertThat(user.getName()).isNotEmpty();
    }

    @Test
    public void whenUsersListIsEmptyThenThrowException() {
        UserGenerator userGenerator = new UserGenerator(new Random());
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                userGenerator::randomUser
        );
        assertThat(exception.getMessage()).isEqualTo("Список пользователей пуст, используйте generate()");
    }
}
