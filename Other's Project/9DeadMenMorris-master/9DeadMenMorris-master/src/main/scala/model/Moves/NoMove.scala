package main.scala.model.Moves

import it.unibo.ai.didattica.mulino.actions.Action

/**
 * Created by tmnd on 29/05/14.
 */

object NoMove extends Move{
  def d = null
  override def toStr : String = "NM" //TODO
  override def toAction : Action = null
}