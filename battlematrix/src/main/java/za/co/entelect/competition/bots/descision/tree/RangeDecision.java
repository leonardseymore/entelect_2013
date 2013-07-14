package za.co.entelect.competition.bots.descision.tree;

public class RangeDecision<T extends Number> extends Decision<T> {

  private T minValue;
  private T maxValue;

  public RangeDecision(DecisionTreeNode trueNode, DecisionTreeNode falseNode, T testValue, T minValue, T maxValue) {
    super(trueNode, falseNode, testValue);
    this.minValue = minValue;
    this.maxValue = maxValue;
  }

  protected boolean getBranch() {
    return maxValue.doubleValue() >= testValue.doubleValue() && testValue.doubleValue() >= minValue.doubleValue();
  }
}
