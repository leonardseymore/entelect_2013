package za.co.entelect.competition.bots.descision.tree;

import java.util.Random;

public class RandomDecision extends Decision {

  private Random random = new Random(System.currentTimeMillis());

  public RandomDecision(DecisionTreeNode trueNode, DecisionTreeNode falseNode) {
    super(trueNode, falseNode, null);
  }

  protected boolean getBranch() {
    return random.nextBoolean();
  }
}
