package com.juliasoft.sonarqube.plugin;


import com.google.common.collect.ImmutableList;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;

@Rule(
		  key = "AvoidRecursiveCallsPlugin",
		  name = "Detect recursive calls",
		  description = "Recursive calls are quite expensive and should always replaced by an iterative solution.",
		  priority = Priority.CRITICAL,
		  tags = {"efficiency"})
public class AvoidRecursiveCallsPlugin extends IssuableSubscriptionVisitor {
 
	  @Override
	  public List<Kind> nodesToVisit() {
		  return ImmutableList.of(Kind.METHOD);
	  }
	  
  @Override
  public void visitNode(Tree tree) {
    MethodTree method = (MethodTree) tree;
    tree.accept(new MethodInvocationTreeVisitor(method));
  }
  
  private class MethodInvocationTreeVisitor extends BaseTreeVisitor {
  		MethodTree callee;
  		public MethodInvocationTreeVisitor(MethodTree method) {
  			callee = method;
  		}
  		
  		@Override 
	  	public void visitMethodInvocation(MethodInvocationTree call) {
	  		if(isRecursiveCall(call, callee))
	          reportIssue(call, "This is a recursive call");
		}
  		
		private boolean isRecursiveCall(MethodInvocationTree call, MethodTree callee) {
			ExpressionTree exp = call.methodSelect();
			String calledMethod = exp.toString();
			String calleeMethod = callee.simpleName().toString();
			if(! calledMethod.equals(calleeMethod)) return false;
			Arguments pars = call.arguments();
			
			List<Type> partypes = callee.symbol().parameterTypes();
			if(pars.size()!=partypes.size()) return false;
			for(int i = 0; i < partypes.size(); i++) {
				
				Type calleeType = partypes.get(i);
				Type calledType = pars.get(i).symbolType();
				if(! (calleeType.equals(calledType) || calledType.isSubtypeOf(calleeType)))
					return false;
			}
			return true;
		  }
  }
  
 
  
}
