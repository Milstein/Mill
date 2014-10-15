package ga

import main.scala.model.Heuristic.ConcreteHeuristic
import it.unibo.ai.didattica.mulino.engine.Engine

/**
 * Created by tmnd on 03/06/14.
 */
object Tester {
  def main(args : Array[String]) = {
    new ServerThread().start
    val white = new ClientThread("white")
    val black = new ClientThread("black")
    println("press to start white")
    readLine()
    white.start()
    println("press to start black")
    readLine()
    black.start()
/*
    costCalculator = new ConcreteHeuristic(v(0),v(1),v(2),v(3),v(4),
      v(5),v(6),v(7),v(8),v(9),
      v(10),v(11),v(12),v(13),v(14),
      v(15),v(16),v(17),v(18))
*/
  }
}
