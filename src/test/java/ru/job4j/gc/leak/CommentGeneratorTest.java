package ru.job4j.gc.leak;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CommentGeneratorTest {
    private final Random random = new Random();
    private final UserGenerator userGenerator = new UserGenerator(random);
    private final CommentGenerator commentGenerator = new CommentGenerator(random, userGenerator);

    @Test
    public void whenGenerateThenCommentsHaveCorrectSize() {
        userGenerator.generate();
        commentGenerator.generate();
        assertThat(commentGenerator.getComments()).hasSize(50);
    }

    @Test
    public void whenGenerateThenCommentNotEmpty() {
        userGenerator.generate();
        commentGenerator.generate();
        assertThat(commentGenerator.getComments().get(0)).isNotNull();
    }

    @Test
    public void whenGenerateThenCommentHaveUser() {
        userGenerator.generate();
        commentGenerator.generate();
        assertThat(commentGenerator.getComments().get(49).getUser()).isNotNull();
    }

    @Test
    public void whenGenerateThenCommentHaveText() {
        userGenerator.generate();
        commentGenerator.generate();
        assertThat(commentGenerator.getComments().get(25).getText()).isNotEmpty();
    }

    @Test
    public void whenCommentsListIsEmptyThenThrowException() {
        Random rand = new Random();
        CommentGenerator commentGenerator = new CommentGenerator(rand, new UserGenerator(rand));
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                commentGenerator::getComments
        );
        assertThat(exception.getMessage()).isEqualTo("Список комментариев пуст, используйте generate()");
    }
}
