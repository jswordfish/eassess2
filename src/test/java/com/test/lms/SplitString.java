package com.test.lms;

public class SplitString {

	public static void main(String[] args) {
		String ex = "Java";
		System.out.println(ex.replaceAll(",.*", ""));
	}
}
