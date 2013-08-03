import za.co.entelect.competition.ai.tankoperator.DummyTankOperator
import za.co.entelect.competition.ai.tankoperator.KeyboardTankOperator
import za.co.entelect.competition.ai.tankoperator.MouseControlledTankOperator

import za.co.entelect.competition.domain.Direction
import za.co.entelect.competition.domain.Ids
import za.co.entelect.competition.domain.Player
import za.co.entelect.competition.domain.Tank
import za.co.entelect.competition.domain.TankAction
import za.co.entelect.competition.groovy.GameFactory

w = 40
h = 40

def getW() {
  return w
}

def getH() {
  return h
}

def getType() {
  return GameFactory.Type.Normal
}

def getMap() {
  return """wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwoww
w                  ww                  w
w                  ww                  w
w                  ww                  w
w               3  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww                  w
w                  ww  1               w
w                  ww                  w
w                  ww                  w
wwywwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww
"""
}

def getTank1() {
  Tank t = new Tank(Ids.Y1, Player.YOU, Direction.UP, new DummyTankOperator())
  return t
}

def getTank2() {
  return new Tank(Ids.Y2, Player.YOU, Direction.DOWN, new DummyTankOperator())
}

def getTank3() {
  Tank t = new Tank(Ids.O1, Player.OPPONENT, Direction.DOWN, new KeyboardTankOperator())
  t.setNextAction(TankAction.FIRE)
  return t
}

def getTank4() {
  return new Tank(Ids.O2, Player.OPPONENT, Direction.LEFT, new DummyTankOperator())
}