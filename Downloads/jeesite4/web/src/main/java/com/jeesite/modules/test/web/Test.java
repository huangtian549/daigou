package com.jeesite.modules.test.web;

public class Test {

	public static void main(String[] args) {
		String picString = "abcdfg,";
		if (picString.endsWith(",")) {
			picString = picString.substring(0, picString.length() - 1);
		}
		System.out.println(picString);
	}

}
