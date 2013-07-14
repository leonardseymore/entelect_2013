package za.co.entelect.competition.bots.descision.tree;

import java.util.Map;

public class MultiDecision<T> implements DecisionTreeNode {

  protected Map<T, DecisionTreeNode> childNodes;
  protected T testValue;

  public MultiDecision(Map<T, DecisionTreeNode> childNodes) {
    this.childNodes = childNodes;
  }

  protected DecisionTreeNode getBranch() {
    return childNodes.get(testValue);
  }

  public DecisionTreeNode makeDecision() {
    DecisionTreeNode branch = getBranch();
    return branch.makeDecision();
  }

  @Override
  public DecisionTreeNodeType getType() {
    return DecisionTreeNodeType.CHOICE;
  }
}
