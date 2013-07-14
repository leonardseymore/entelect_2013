package za.co.entelect.competition.bots.descision.tree;

public class DecisionTreeAction implements DecisionTreeNode {

  public DecisionTreeAction makeDecision() {
    return this;
  }

  @Override
  public DecisionTreeNodeType getType() {
    return DecisionTreeNodeType.ACTION;
  }
}
