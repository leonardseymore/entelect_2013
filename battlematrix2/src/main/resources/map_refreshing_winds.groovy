import za.co.entelect.competition.ai.tankoperator.DummyTankOperator
import za.co.entelect.competition.ai.tankoperator.KeyboardTankOperator
import za.co.entelect.competition.ai.tankoperator.MouseControlledTankOperator
import za.co.entelect.competition.ai.tankoperator.SquadTankOperator
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
  return """w3      w o w     4w
w       wwwww      w
w  w               w
w  w               w
w  wwwwww  w       w
w          w       w
www wwww wwwwwww www
w                  w
w  wwww w       w  w
w  w    w  www  w  w
w  w  www  w    w  w
w  w       w wwww  w
w                  w
www wwwwwww wwww www
w       w          w
w       w  wwwwww  w
w               w  w
w               w  w
w       wwwww      w
        w1y w  2    """
}

t1 = new Tank(Ids.Y1, Player.YOU, Direction.UP, new SquadTankOperator())
t2 = new Tank(Ids.Y2, Player.YOU, Direction.UP, new SquadTankOperator())
t3 = new Tank(Ids.O1, Player.OPPONENT, Direction.DOWN, new SquadTankOperator())
t4 = new Tank(Ids.O2, Player.OPPONENT, Direction.RIGHT, new SquadTankOperator())

def getTank1() {
  return t1
}

def getTank2() {
  return t2
}

def getTank3() {
  t3.getBlackboard().setTarget(t2)
  return t3
}

def getTank4() {
  return t4
}
