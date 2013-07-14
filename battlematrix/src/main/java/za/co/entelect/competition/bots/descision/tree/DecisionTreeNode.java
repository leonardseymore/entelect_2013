package za.co.entelect.competition.bots.descision.tree;

public interface DecisionTreeNode {
  DecisionTreeNode makeDecision();
  DecisionTreeNodeType getType();
}
