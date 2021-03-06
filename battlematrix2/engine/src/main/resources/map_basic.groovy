import za.co.entelect.competition.ai.tankoperator.DummyTankOperator
import za.co.entelect.competition.ai.tankoperator.KeyboardTankOperator
import za.co.entelect.competition.ai.tankoperator.MouseControlledTankOperator

import za.co.entelect.competition.domain.Direction
import za.co.entelect.competition.domain.Ids
import za.co.entelect.competition.domain.Player
import za.co.entelect.competition.domain.Tank
import za.co.entelect.competition.groovy.GameFactory

w = 20
h = 20

def getW() {
  return w
}

def getH() {
  return h
}

def getType() {
  return GameFactory.Type.By5
}

def getMap() {
  return """wwwwwwwwwwwwwwwwwwww
w                  w
w               o  w
w               w  w
w       4       3  w
w               w  w
w                  w
w                  w
w                  w
w                  w
w                  w
w                  w
w                  w
w                  w
w  w               w
w  1      2        w
w  w               w
w  y               w
w                  w
wwwwwwwwwwwwwwwwwwww"""
}

def getTank1() {
  return new Tank(Ids.Y1, Player.YOU, Direction.UP, new DummyTankOperator())
}

def getTank2() {
  return new Tank(Ids.Y2, Player.YOU, Direction.DOWN, new DummyTankOperator())
}

def getTank3() {
  return new Tank(Ids.O1, Player.OPPONENT, Direction.RIGHT, new MouseControlledTankOperator())
}

def getTank4() {
  return new Tank(Ids.O2, Player.OPPONENT, Direction.LEFT, new KeyboardTankOperator())
}