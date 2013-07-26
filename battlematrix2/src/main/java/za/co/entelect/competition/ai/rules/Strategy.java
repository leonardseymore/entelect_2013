package za.co.entelect.competition.ai.rules;

import za.co.entelect.competition.domain.Tank;

public class Strategy {
  private Directive y1Directive;
  private Directive y2Directive;

  public Strategy(Directive y1Directive, Directive y2Directive) {
    this.y1Directive = y1Directive;
    this.y2Directive = y2Directive;
  }

  public Directive getY1Directive() {
    return y1Directive;
  }

  public void setY1Directive(Directive y1Directive) {
    this.y1Directive = y1Directive;
  }

  public Directive getY2Directive() {
    return y2Directive;
  }

  public void setY2Directive(Directive y2Directive) {
    this.y2Directive = y2Directive;
  }
}
