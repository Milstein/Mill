package ga

/**
 * Created by tmnd on 03/06/14.
 */
class ClientThread(player : String, gene : String = null) extends Thread{
  override def run = {
    if (gene == null)
      main.scala.Client.main(Array(player))
    else
      main.scala.Client.main(Array(player,gene))
  }
}
