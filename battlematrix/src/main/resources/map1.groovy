import za.co.entelect.competition.bots.tanks.DummyTank
import za.co.entelect.competition.bots.tanks.KeyboardControlledTank
import za.co.entelect.competition.bots.tanks.RandomTank
import za.co.entelect.competition.domain.Tank

w = 20
h = 20

def getSize() {
  return [w * 5, h * 5] as int[]
}

def getMap() {
  return """wwwwwwwwwwwwwwwwwwww
w                  w
w  w         4wo   w
w  w         www   w
w  wwwwww  w  w3   w
w          w       w
w  w    wwww  wwwwww
w  w               w
w  w ww    w    w  w
w  w      w     w  w
w  w     w   ww w  w
w  w    w       w  w
w               w  w
wwwwww  wwww    w  w
w       w          w
w   1w  w  wwwwww  w
w   www         w  w
w   yw2         w  w
w                  w
wwwwwwwwwwwwwwwwwwww"""
}

def getTank1() {
  return new KeyboardControlledTank(Tank.TankId.P1T1)
}

def getTank2() {
  return new DummyTank(Tank.TankId.P1T2)
}

def getTank3() {
  return new DummyTank(Tank.TankId.P2T1)
}

def getTank4() {
  return new RandomTank(Tank.TankId.P2T2)
}