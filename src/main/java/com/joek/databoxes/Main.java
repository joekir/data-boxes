package com.joek.databoxes;

import static com.joek.databoxes.DataBox.*;

public class Main {
	public static void main (String[] args) {
		Box<Integer> foo = new Box<>(10);
		System.out.println((foo + 50).getInner());
	}
}

