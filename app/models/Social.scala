package models

import scalikejdbc._
import skinny.orm._

case class Social(
                    id:Long,
                    email:String,
                    share:Option[String]
                  )

object Social extends SkinnyCRUDMapper[Social] {
  override def tableName = "socials"
  override lazy val defaultAlias = createAlias("so")

  override def columnNames = Seq("id", "email", "share")
  override def extract(rs: WrappedResultSet, rn: ResultName[Social]):Social = Social(
    id = rs.get(rn.id),
    email = rs.get(rn.email),
    share = rs.get(rn.share)
  )

  def verify(email:String): Option[Social] =  findBy(sqls.eq(defaultAlias.email, email))
}
