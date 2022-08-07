package com.joek.databoxes;

import static com.joek.databoxes.DataBox.Box;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Box<Integer> foo = new Box<>(10);
        Box<Integer> someShitIDoWithData = foo + 10;
        assert someShitIDoWithData.getInner() == 20;
        System.out.printf("Added some numbers together to get: %d\n", someShitIDoWithData.getInner());
    }
}

