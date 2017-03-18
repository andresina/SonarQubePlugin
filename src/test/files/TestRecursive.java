package com.juliasoft.sonarqube.plugin;

public class TestClass {

		  int foo(int i) {
			  return i;
		  }
		  
	      int foo8(Object a) {
	    	  return foo8(null); // Noncompliant
	      }
	      
		  int foo8(int value) {
			  if (value<2) 
				  return 1;
			  else 
				  return 1+foo(value-1)+foo(value-2);
		  }
}
