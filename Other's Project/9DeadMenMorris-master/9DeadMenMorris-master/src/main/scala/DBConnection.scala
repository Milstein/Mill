package main.scala

import scala.slick.driver.H2Driver.simple._

/**
 * Created by tmnd on 31/05/14.
 */
object DBConnection {
  val conn = Database.forURL("jdbc:h2:mem:9deadmensmorris;DB_CLOSE_DELAY=-1", driver="org.h2.Driver")
  var read = 0
  var write = 0
}
