package ga

/**
 * Created by tmnd on 03/06/14.
 */
class ServerThread extends Thread{
  override def run = {
    it.unibo.ai.didattica.mulino.engine.Engine.main(Array())
  }
}
