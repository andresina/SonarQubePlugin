package com.juliasoft.sonarqube.plugin;

public class TestClass {
	
		  int     foo1() { return 0; }
		  void    foo2(int value) { }
		  int     foo3(int value) { return 0; } // Noncompliant
		  Object  foo4(int value) { return null; }
		  TestClass foo5(TestClass value) {return null; } // Noncompliant
		 
		  int     foo6(int value, String name) { return 0; }
		  int     foo7(int ... values) { return 0;}
		  
		
}
