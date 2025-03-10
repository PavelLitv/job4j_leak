package ru.job4j.gc.leak;

import ru.job4j.gc.leak.models.Post;

import java.util.Random;
import java.util.Scanner;

public class Menu {
    public static final int ADD_POST = 1;
    public static final int ADD_MANY_POST = 2;
    public static final int SHOW_ALL_POSTS = 3;
    public static final int DELETE_POSTS = 4;
    public static final int DELETE_BY_ID = 5;

    public static final String SELECT = "Выберите меню";
    public static final String COUNT = "Выберите количество создаваемых постов";
    public static final String TEXT_OF_POST = "Введите текст";
    public static final String ENTER_ID = "Введите id поста";
    public static final String EXIT = "Конец работы";

    public static final String MENU = """
                Введите 1 для создание поста.
                Введите 2, чтобы создать определенное количество постов.
                Введите 3, чтобы показать все посты.
                Введите 4, чтобы удалить все посты.
                Введите 5, чтобы удалить пост по id.
                Введите любое другое число для выхода.
            """;

    public static void main(String[] args) {
        Random random = new Random();
        UserGenerator userGenerator = new UserGenerator(random);
        CommentGenerator commentGenerator = new CommentGenerator(random, userGenerator);
        Scanner scanner = new Scanner(System.in);
        PostStore postStore = new PostStore();
        start(commentGenerator, scanner, userGenerator, postStore);
    }

    private static void start(CommentGenerator commentGenerator, Scanner scanner, UserGenerator userGenerator, PostStore postStore) {
        boolean run = true;
        while (run) {
            System.out.println(MENU);
            System.out.println(SELECT);
            int userChoice = Integer.parseInt(scanner.nextLine());
            System.out.println(userChoice);
            if (ADD_POST == userChoice) {
                System.out.println(TEXT_OF_POST);
                String text = scanner.nextLine();
                var post = createPost(commentGenerator, userGenerator, postStore, text);
                System.out.println("Generate: " + post.getId());
            } else if (ADD_MANY_POST == userChoice) {
                System.out.println(TEXT_OF_POST);
                String text = scanner.nextLine();
                System.out.println(COUNT);
                String count = scanner.nextLine();
                System.out.printf("Memory used %.2fmb\n", memUsage());
                for (int i = 0; i < Integer.parseInt(count); i++) {
                    System.out.printf("\rGenerate %.2f%% %.2fMb",
                            ((double) i / Integer.parseInt(count)) * 100,
                            memUsage());
                    createPost(commentGenerator, userGenerator, postStore, text);
                }
                System.out.printf("\nMemory used %.2fmb\n", memUsage());
            } else if (SHOW_ALL_POSTS == userChoice) {
                System.out.println(postStore.getPosts());
            } else if (DELETE_POSTS == userChoice) {
                System.out.println("Удаление всех постов ...");
                postStore.removeAll();
            } else if (DELETE_BY_ID == userChoice) {
                System.out.println(ENTER_ID);
                int id = Integer.parseInt(scanner.nextLine());
                System.out.println("Delete post with id: " + id);
                postStore.removeById(id);
            } else {
                run = false;
                System.out.println(EXIT);
            }
        }
    }

    private static double memUsage() {
        var rt = Runtime.getRuntime();
        var totalMem = rt.totalMemory();
        var freeMem = rt.freeMemory();
        var usedMem = totalMem - freeMem;
        return (double) usedMem / 1024 / 1024;
    }

    private static Post createPost(CommentGenerator commentGenerator,
                                   UserGenerator userGenerator,
                                   PostStore postStore, String text) {
        userGenerator.generate();
        commentGenerator.generate();
        var post = new Post();
        post.setText(text);
        post.setComments(commentGenerator.getComments());
        postStore.add(post);
        return post;
    }
}