import za.co.entelect.competition.ai.tankoperator.DummyTankOperator
import za.co.entelect.competition.ai.tankoperator.KeyboardTankOperator
import za.co.entelect.competition.ai.tankoperator.MouseControlledTankOperator

import za.co.entelect.competition.domain.Direction
import za.co.entelect.competition.domain.Ids
import za.co.entelect.competition.domain.Player
import za.co.entelect.competition.domain.Tank
import za.co.entelect.competition.domain.TankAction

w = 20
h = 20

def getSize() {
  return [w * 5, h * 5] as int[]
}

def getMap() {
  return """wwwwwwwwwwowwwwwwwww
w                  w
w                  w
w                  w
w                  w
w                  w
w                  w
w                  w
wwwwwwwwwwwwwwwwwwww
w  1            3  w
wwwwwwwwwwwwwwwwwwww
w                  w
w                  w
w                  w
w                  w
w                  w
w                  w
w                  w
w                  w
wwwwwwwwwywwwwwwwwww"""
}

def getTank1() {
  Tank t = new Tank(Ids.Y1, Player.YOU, Direction.RIGHT, new DummyTankOperator())
  return t
}

def getTank2() {
  return new Tank(Ids.Y2, Player.YOU, Direction.DOWN, new DummyTankOperator())
}

def getTank3() {
  Tank t = new Tank(Ids.O1, Player.OPPONENT, Direction.LEFT, new MouseControlledTankOperator())
  t.setNextAction(TankAction.FIRE)
  return t
}

def getTank4() {
  return new Tank(Ids.O2, Player.OPPONENT, Direction.LEFT, new KeyboardTankOperator())
}