package za.co.entelect.competition.ai.planning;

import java.util.*;

public class GameModel implements Cloneable {

  Set<GameModelProp> props;

  public GameModel() {
    props = new HashSet<>();
  }

  public GameModel(Set<GameModelProp> props) {
    this.props = props;
  }

  public Collection<GameModelProp> getProps() {
    return props;
  }

  public Object getProp(GameModelPropKey key) {
    for (GameModelProp prop : props) {
      if (prop.key == key) {
        return prop.value;
      }
    }
    return null;
  }

  public void addProp(GameModelProp prop) {
    props.add(prop);
  }

  public boolean isSatisfiedBy(GameModel gameModel) {
    for (GameModelProp prop : props) {
      if (!prop.value.equals(gameModel.getProp(prop.key))) {
        return false;
      }
    }
    return true;
  }

  public GameModel clone() {
    Set<GameModelProp> newProps = new HashSet<>();
    for (GameModelProp prop : props) {
      newProps.add(prop);
    }
    GameModel clone = new GameModel(newProps);
    return clone;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(props.toString());
    return builder.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GameModel)) return false;

    GameModel gameModel = (GameModel) o;

    if (props != null ? !props.equals(gameModel.props) : gameModel.props != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return props != null ? props.hashCode() : 0;
  }
}

