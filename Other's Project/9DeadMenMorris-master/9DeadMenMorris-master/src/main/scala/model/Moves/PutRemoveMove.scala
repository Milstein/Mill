package main.scala.model.Moves

import main.scala.model.Position
import it.unibo.ai.didattica.mulino.actions.{Phase1Action, Action}

/**
 * Created by tmnd on 29/05/14.
 */
case class PutRemoveMove(d : Position, toRemove : Position) extends Move{

  override def toString : String = "PutRemoveMove "+d.name+" // "+toRemove.name
  override def toStr : String = PutRemoveMove.PREFIX+d.name+toRemove.name
  override def toAction : Action = {
    val toRet = new Phase1Action()
    toRet.setPutPosition(d.coordinates)
    toRet.setRemoveOpponentChecker(toRemove.coordinates)
    return toRet
  }
}
object PutRemoveMove{
  val PREFIX = "PR"
}