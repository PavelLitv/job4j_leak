package ru.job4j.gc.ref;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SoftReferenceExample {
    public static void main(String[] args) {
        Random rand = new Random();
        SoftReference<List<People>> someData = generatePeopleList(100);
        List<People> peopleListStrong = someData.get();
        if (peopleListStrong != null) {
            People people = peopleListStrong.get(rand.nextInt(peopleListStrong.size()));
            if (people.age > 17) {
                System.out.println("Welcome to SoftReferenceExample!!!");
            } else {
                System.out.println("You are still young");
            }
        } else {
            System.out.println("People list is empty");
        }
    }

    private static SoftReference<List<People>> generatePeopleList(int count) {
        List<People> peopleList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            peopleList.add(new People(i));
        }
        return new SoftReference<>(peopleList);
    }

    private static class People {
        int age;

        People(int age) {
            this.age = age;
        }
    }
}
