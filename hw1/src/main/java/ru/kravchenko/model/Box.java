package ru.kravchenko.model;

public class Box {
    private final Long id;
    String color;
    public int size;

    public Box(Long id, String color, int size) {
        this.id = id;
        this.color = color;
        this.size = size;
    }
}
