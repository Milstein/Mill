package main.scala.model.Tree

import main.scala.DBConnection
import main.scala.model.Heuristic.Heuristic
import main.scala.model.{StateGenerator, MyState}
import scala.slick.driver.H2Driver.simple._

/**
 * Created by tmnd on 31/05/14.
 */
case class SlickNode(id : Option[Long], parentId : Option[Long], stateString : String)(implicit val costCalculator : Heuristic = Heuristic.defaultHeuristic) extends AbstractNode{
  private var _parent : AbstractNode = null
  private var _childrenGenerated = false
  private var _childrens : Iterable[AbstractNode] = null
  private var _data : MyState = null
  private var _cost : Float = -1


  override def parent = {
    if (_parent == null)
      _parent = SlickNode.findById(parentId) match{
        case x : Some[SlickNode] => x.get
        case None                => null
      }
    _parent
  }

  override def cost ={
    if (_cost == -1)
      _cost = costCalculator.calc(if (parent==null) null else parent.data,data,data.toMove)
    _cost
  }
  override def data ={
    if (_data == null)
      _data = MyState.stateFromStr(stateString)
    _data
  }

  override def eraseParent: Unit = {
    //SlickNode.deleteParents(id.get)
    _parent = null
  }

  override def childrens: Iterable[AbstractNode] = {
    if (!_childrenGenerated){
      SlickNode.addAll(StateGenerator.nextStates(data).map(s => new SlickNode(None,id,s.toStateString)))
      _childrenGenerated = true
      //_childrens = SlickNode.findByParentId(id)
    }
    //_childrens
    SlickNode.findByParentId(id)
  }

  override def costCalculatorInstance = costCalculator

}

object SlickNode {
  val nodes = TableQuery[SlickNodes]
  implicit val session = DBConnection.conn.createSession()
  def create = nodes.ddl.create
  def drop = nodes.ddl.drop

  def findById(x : Option[Long]) : Option[SlickNode] = nodes.filter(_.id === x).firstOption

  def findByParentId(x : Option[Long]) : Iterable[SlickNode] = nodes.filter(_.parentId === x).list

  def addAll(x : Iterable[SlickNode]) = nodes ++= x

  def root = nodes.list.head

  def clean(n : SlickNode) = {
    n.eraseParent
    nodes.filterNot(_.id === n.id).delete
  }
}

class SlickNodes(tag: Tag) extends Table[SlickNode](tag, "NODES") {

  def id = column[Long]("NODE_ID", O.PrimaryKey, O.AutoInc)

  def parentId = column[Option[Long]]("PARENT")

  def stateString = column[String]("STATE")

  //def parent = foreignKey("PARENT_FK", parentId, SlickNode.nodes)(_.id)

  def * = (id.?, parentId, stateString) <> ((SlickNode.apply _).tupled , SlickNode.unapply)
}