package main.scala.model.Moves

import main.scala.model.Position
import it.unibo.ai.didattica.mulino.actions.{Phase1Action, Action}

/**
 * Created by tmnd on 29/05/14.
 */
case class PutMove(d : Position) extends Move{

  override def toString : String = "PutMove "+d.name
  override def toStr : String = PutMove.PREFIX+d.name
  override def toAction : Action = {
    val toRet = new Phase1Action()
    toRet.setPutPosition(d.coordinates)
    toRet.setRemoveOpponentChecker(null)
    return toRet
  }
}
object PutMove{
  val PREFIX = "PM"
}