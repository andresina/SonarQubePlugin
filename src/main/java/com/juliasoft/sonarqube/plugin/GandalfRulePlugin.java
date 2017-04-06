package com.juliasoft.sonarqube.plugin;

import com.google.common.collect.ImmutableList;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol.MethodSymbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;

@Rule(key = "NoMoreThanTwoNestedLoopPlugin", name = "No more than two nested loop allowed", description = "If two or more nested loop are found in the code a warning is rised", priority = Priority.INFO, tags = {
		"style" })

public class GandalfRulePlugin extends IssuableSubscriptionVisitor {

	@Override
	public List<Kind> nodesToVisit() {
		final Kind[] CONT = { Kind.WHILE_STATEMENT, Kind.FOR_STATEMENT, Kind.DO_STATEMENT, Kind.FOR_EACH_STATEMENT };
		return ImmutableList.copyOf(CONT);
	}

	@Override
	public void visitNode(Tree tree) {
		int i = 0;
		StatementTree par = (StatementTree) tree;
		boolean methodsFound = false;

		while (methodsFound == false) {
			if (par.is(Kind.FOR_STATEMENT) || par.is(Kind.FOR_EACH_STATEMENT) || par.is(Kind.WHILE_STATEMENT)
					|| par.is(Kind.DO_STATEMENT)) {
				i++;
			}
			if (par.parent().is(Kind.BLOCK)) {
				if (par.parent().parent().is(Kind.METHOD)) {
					methodsFound = true;
				} else {
					par = (StatementTree) par.parent().parent();
				}
			} else { // se mio "padre" non è di tipo block potrebbe essere o
						// "LOOP_STATEMENT" o "IF_STATEMENT"
				par = (StatementTree) par.parent();
			}
		}
		if (i >= 3) {
			reportIssue(tree, "More than 2 nested loop");
		}
	}

}
