package ru.job4j.gc.ref;

import java.lang.ref.WeakReference;
import java.util.*;

public class WeakReferenceExample {
    private static final Map<Integer, WeakReference<People>> SOME_PEOPLE = new HashMap<>();

    public static void main(String[] args) {
        Random rand = new Random();
        for (int i = 0; i < 15; i++) {
            People people = getPeopleFromPoolByAge(rand.nextInt(50, 60));
            System.out.println(people);
        }
    }

    private static People getPeopleFromPoolByAge(int age) {
        WeakReference<People> peopleWeak = SOME_PEOPLE.get(age);
        People people = (peopleWeak == null) ? null : peopleWeak.get();
        if (people == null) {
            people = new People(age);
            SOME_PEOPLE.put(age, new WeakReference<>(people));
            System.out.printf("Add new people with age %d to map\n", age);
        }
        return people;
    }

    private static class People {
        int age;

        People(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "People{"
                    + "age=" + age
                    + '}';
        }
    }
}
