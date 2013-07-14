import za.co.entelect.competition.bots.tanks.KeyboardControlledTank
import za.co.entelect.competition.bots.tanks.MouseControlledTank
import za.co.entelect.competition.bots.tanks.TimmyTank
import za.co.entelect.competition.domain.TankId

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
w  w ww w  w    w  w
w  w    w  w    w  w
w  w    w  w ww w  w
w  w    w  w    w  w
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
  return new KeyboardControlledTank(TankId.Y1)
}

def getTank2() {
  return new MouseControlledTank(TankId.Y2)
}

def getTank3() {
  return new MouseControlledTank(TankId.O1)
}

def getTank4() {
  return new TimmyTank(TankId.O2)
}