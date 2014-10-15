package main.scala.model.Moves

import main.scala.model.Position
import it.unibo.ai.didattica.mulino.actions._
/**
 * Created by tmnd on 29/05/14.
 */
case class FlyMove(o : Position, d : Position) extends Move{
  override def toString : String = "FlyMove "+o.name+" -> "+d.name
  override def toStr : String = FlyMove.PREFIX+o.name+d.name
  override def toAction : Action = {
    val toRet = new PhaseFinalAction()
    toRet.setFrom(o.coordinates)
    toRet.setTo(d.coordinates)
    toRet.setRemoveOpponentChecker(null)
    return toRet
  }
}
object FlyMove{
  val PREFIX = "FM"
}