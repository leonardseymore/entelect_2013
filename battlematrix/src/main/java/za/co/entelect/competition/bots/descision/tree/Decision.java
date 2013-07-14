package za.co.entelect.competition.bots.descision.tree;

public abstract class Decision<T> implements DecisionTreeNode {

  protected DecisionTreeNode trueNode;
  protected DecisionTreeNode falseNode;
  protected T testValue;

  protected Decision(DecisionTreeNode trueNode, DecisionTreeNode falseNode, T testValue) {
    this.trueNode = trueNode;
    this.falseNode = falseNode;
    this.testValue = testValue;
  }

  protected abstract boolean getBranch();

  public DecisionTreeNode makeDecision() {
    if (getBranch()) {
      if (trueNode == null) {
        return null;
      } else {
        return trueNode.makeDecision();
      }
    } else {
      if (falseNode == null) {
        return null;
      } else {
        return falseNode.makeDecision();
      }
    }
  }

  @Override
  public DecisionTreeNodeType getType() {
    return DecisionTreeNodeType.CHOICE;
  }
}
