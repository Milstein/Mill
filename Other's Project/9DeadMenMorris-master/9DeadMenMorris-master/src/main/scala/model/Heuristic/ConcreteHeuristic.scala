package main.scala.model.Heuristic

import main.scala.model.{MyPhase, MyState}
import scala.util.Random

/**
 * Created by tmnd on 25/05/14.
 */
class ConcreteHeuristic(val p1_nmr : Float = 10, //factor to mul the number of morris closed before
                        val p1_cls : Float = 100, //factor to add if this move closes a morris
                        val p1_owbp : Float = 2,//factor to mul the number of own pieces blocked
                        val p1_opbp : Float = 2, //factor to mul the number of oppo pieces blocked
                        val p1_npc : Float = 5, //factor to mul the number of pieces owned
                        val p1_2pc : Float = 5, //factor to mul the number of pieces in the same line and the other one is empty
                        val p1_3pc : Float = 5, //factor to mul the number of pieces that offers to close 2 morris
                        val p2_cls : Float = 5,
                        val p2_nmr : Float = 5,
                        val p2_owbp : Float = 2,
                        val p2_opbp : Float = 2,
                        val p2_npc : Float = 2,
                        val p2_opm : Float = 5,//opened morris
                        val p2_dom : Float = 5,//doubleMorris
                        val p2_win : Float = 10000,
                        val p3_2pc : Float = 10,
                        val p3_3pc : Float = 20,
                        val p3_cls : Float = 1000,
                        val p3_win : Float = 10000) extends Heuristic{

  val r = new Random()
  private def nextRandom : Float = {
    Math.abs(r.nextGaussian/Double.MaxValue*2).toFloat
  }
  override def calc(actual : MyState, future : MyState, player : Boolean) : Float ={
    actual.phase match{
      case MyPhase.Phase1 => calcFirst(actual, future, player)
      case MyPhase.Phase2 => calcSecond(actual, future, player)
      case MyPhase.Phase3 => calcThird(actual, future, player)
      case _ => throw new Exception("Nonsense Phase")
    }
  }
  private def calcFirst(actual : MyState, future : MyState, player : Boolean) : Float ={
    val closedMills = future.closedMills(player)
    val closesMill = if (actual != null && actual.moveCreatesMill(future.move,player)) 1
                     else 0
    val ownBlockedPieces = future.blockedPieces(player)
    val oppoBlockedPieces = future.blockedPieces(!player)
    val ownedPieces = future.onBoard(player)
    val twoPcsConf = future.twoPcsConf(player)
    val threePcsConf = future.threePcsConf(player)
    (closedMills * p1_nmr + closesMill * p1_cls + ownBlockedPieces * p1_owbp + oppoBlockedPieces * p1_opbp + ownedPieces * p1_npc +
      twoPcsConf * p1_2pc + threePcsConf * p1_3pc) * nextRandom
  }
  private def calcSecond(actual : MyState, future : MyState, player : Boolean) : Float ={
    val closedMills = future.closedMills(player)
    val closesMill = if (actual != null && actual.moveCreatesMill(future.move,player)) 1
                     else 0
    val ownBlockedPieces = future.blockedPieces(player)
    val oppoBlockedPieces = future.blockedPieces(!player)
    val ownedPieces = future.onBoard(player)
    val won = if (future.hasWon(player)) 1 else 0
    val openedMorris = future.openedMorris(player)
    val doubleMorris = future.doubleMorris(player)
    (closesMill * p2_cls + closedMills * p2_nmr + ownBlockedPieces * p2_owbp + oppoBlockedPieces * p2_opbp + ownedPieces * p2_npc +
      openedMorris * p2_opm + doubleMorris * p2_dom + won * p2_win) * nextRandom
  }

  private def calcThird(actual : MyState, future : MyState, player : Boolean) : Float ={
    val closedMills = future.closedMills(player)
    val closesMill = if (actual != null && actual.moveCreatesMill(future.move,player)) 1
                     else 0
    val twoPcsConf = future.twoPcsConf(player)
    val threePcsConf = future.threePcsConf(player)
    val won = if (future.hasWon(player)) 1 else 0
    (p3_cls * closesMill + p3_2pc * twoPcsConf + p3_3pc * threePcsConf + p3_win * won) * nextRandom
  }

  override def toString : String = p2_cls.toString
}
