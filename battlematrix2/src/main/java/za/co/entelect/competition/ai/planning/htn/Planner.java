package za.co.entelect.competition.ai.planning.htn;

import za.co.entelect.competition.ai.planning.goap.GameModel;
import za.co.entelect.competition.ai.planning.goap.GameModelProp;
import za.co.entelect.competition.domain.GameState;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Planner {
  private String desc;
  protected GameModel state;
  protected List<Planner> children;
  protected Iterator<Planner> childIterator;

  protected Collection<GameModelProp> preconditions = new ArrayList<>();
  protected Collection<GameModelProp> effects = new ArrayList<>();

  public Planner(String desc) {
    this(null, desc);
  }

  public Planner(GameModel state, String desc) {
    this.state = state;
    this.desc = desc;
    this.children = new CopyOnWriteArrayList<>();
  }

  public GameModel getState() {
    return state;
  }

  public void setState(GameModel state) {
    this.state = state;
  }

  public Collection<GameModelProp> getPreconditions() {
    return preconditions;
  }

  public boolean isSatisfiedBy(GameModel state) {
    return state.isSatisfiedBy(state);
  }

  public Collection<GameModelProp> getEffects() {
    return effects;
  }

  public void addChild(Planner planner) {
    children.add(planner);
  }

  public List<Planner> getChildren() {
    return children;
  }

  public Planner nextChild() {
    if (childIterator == null) {
      childIterator = children.iterator();
    }
    while (childIterator.hasNext()) {
      Planner child = childIterator.next();
      child.state = state;
      if (child.isSatisfiedBy(state)) {
        return child;
      }
    }
    return null;
  }

  public String getDesc() {
    return desc;
  }
}
