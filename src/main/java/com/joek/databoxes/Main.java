package com.joek.databoxes;

public class Main {
	public static void main (String[] args) {
		System.out.println("Hello, World.");

		DataBox foo = new DataBox(10);
//		Databox bar = foo + 3;

		System.out.println(foo.getNum());
	}
}
