package com.juliasoft.sonarqube.plugin;

public class TestClass {

	public int fun(int x) {
		int z = 0, y;
		while (x <= 100) {
			y = x / 2 + 1;
			while (y < 1000) {
				z = z + x + y;
				y = y * 2;
			}
		}
		return z;
	}

	public void foo(int a) {
		in i, x = 0;
		while (i < 10) {
			for (j = 0; j < 5; j++)
				x++;
			i++;
		}
		System.out.println(x);
	}

public int foo2(boolean b, int a) {
	while (b) {
		while (a < 30) {
			for (int i = 0; i < 10; i++) // Noncompliant
				a = a + 2;
		}
		b = false;
	}
	return a;
}

	public boolean foo3(int c) {
		int a = 10;
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				while (j > a) { // Noncompliant
					c++;
				}
			}
			return (c > 1000);
		}
	}

	public boolean foo4(int c){
		int a;
		for(int i = 0; i < 100; ++i) {
		    a = i / 2;
			for(int j = 0; j < 100; ++j) {
				while(j > a) { // Noncompliant
					++c;
				}
			}
		}
		return (c > 1000);
	}
}
