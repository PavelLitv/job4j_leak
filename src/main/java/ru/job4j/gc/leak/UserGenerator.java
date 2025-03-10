package ru.job4j.gc.leak;

import ru.job4j.gc.leak.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserGenerator implements Generate {
    private List<String> names;
    private List<String> surnames;
    private List<String> patrons;
    private final List<User> users = new ArrayList<>();
    private final Random random;

    public UserGenerator(Random random) {
        this.random = random;
    }

    @Override
    public void generate() {
        if (users.isEmpty()) {
            readAll();
            int newUsers = 1000;
            for (int i = 0; i < newUsers; i++) {
                StringBuilder nameBuilder = new StringBuilder();
                String separator = " ";
                nameBuilder.append(surnames.get(random.nextInt(surnames.size()))).append(separator)
                        .append(names.get(random.nextInt(names.size()))).append(separator)
                        .append(patrons.get(random.nextInt(patrons.size())));
                var user = new User();
                user.setName(nameBuilder.toString());
                users.add(user);

            }
        }
    }

    private void readAll() {
        try {
            names = read("files/names.txt");
            surnames = read("files/surnames.txt");
            patrons = read("files/patr.txt");
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public User randomUser() {
        if (users.isEmpty()) {
            throw new IllegalStateException("Список пользователей пуст, используйте generate()");
        }
        return users.get(random.nextInt(users.size()));
    }
}
