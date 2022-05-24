package com.joek.databoxes;

import static com.joek.databoxes.DataBox.Box;

public class Main {
	public static void main (String[] args) {
		Box<Integer> foo = new Box<>(10);
		var someShitIDoWithData = foo + 50;
		System.out.println(someShitIDoWithData.getInner());
	}
}

