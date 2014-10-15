package util

import collection.JavaConversions._
import it.unibo.ai.didattica.mulino.actions._
import it.unibo.ai.didattica.mulino.domain.State
import main.scala.model.Moves.{NoMove, Move}
import main.scala.model.{MyPhase, Position, MyState}
import main.scala.model.Tree.{SlickNode, AbstractNode}

/**
 * Created by tmnd on 01/06/14.
 */
object Converter {
  implicit def Move2Action(x : Move) : Action = x.toAction

  implicit def State2Node(x : State)(implicit toMove : Boolean) : AbstractNode = {
    val move = NoMove
    val phase = x.getCurrentPhase match{
      case State.Phase.FIRST  => MyPhase.Phase1
      case State.Phase.SECOND => MyPhase.Phase2
      case State.Phase.FINAL  => MyPhase.Phase3
    }
    var positions : Map[String,Position] = Map[String,Position]()
    for (k <- x.getBoard.keySet){
      val c = x.getBoard.get(k)
      val uk = k.toUpperCase
      positions += (uk -> new Position(uk,c match{
        case State.Checker.WHITE => Some(true)
        case State.Checker.BLACK => Some(false)
        case State.Checker.EMPTY => None
      }))
    }
    val whiteUsed = (x.getWhiteCheckers+x.getWhiteCheckersOnBoard)
    val blackUsed = (x.getBlackCheckers+x.getBlackCheckersOnBoard)
    val removed : Map[Boolean,Int] = Map(true -> (9 - whiteUsed),
                                         false-> (9 - blackUsed))
    new SlickNode(None, None, new MyState(toMove, move, phase, positions, removed).toStateString)
  }
}
