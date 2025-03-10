package ru.job4j.gc.leak;

import ru.job4j.gc.leak.models.Comment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommentGenerator implements Generate {
    private final String separator = System.lineSeparator();
    private final List<Comment> comments = new ArrayList<>();
    private List<String> phrases;
    private final UserGenerator userGenerator;
    private final Random random;

    public CommentGenerator(Random random, UserGenerator userGenerator) {
        this.userGenerator = userGenerator;
        this.random = random;
    }

    private void read() {
        try {
            phrases = read("files/phrases.txt");
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<Comment> getComments() {
        if (comments.isEmpty()) {
            throw new IllegalStateException("Список комментариев пуст, используйте generate()");
        }
        return comments;
    }

    @Override
    public void generate() {
        if (comments.isEmpty()) {
            read();
            int count = 50;
            for (int i = 0; i < count; i++) {
                StringBuilder phraseBuilder = new StringBuilder();
                phraseBuilder
                        .append(phrases.get(random.nextInt(phrases.size()))).append(separator)
                        .append(phrases.get(random.nextInt(phrases.size()))).append(separator)
                        .append(phrases.get(random.nextInt(phrases.size())));
                var comment = new Comment();
                comment.setText(phraseBuilder.toString());
                comment.setUser(userGenerator.randomUser());
                comments.add(comment);
            }
        }
    }
}
