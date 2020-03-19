package controllers

import javax.inject.Inject
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class AuthenticatedUserAction @Inject() (parser: BodyParsers.Default)(implicit ec: ExecutionContext)
  extends ActionBuilderImpl(parser) {

  private val logger = play.api.Logger(this.getClass)

  private val SESSION_USERNAME_KEY = "username"

  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    logger.info("ENTERED AuthenticatedUserAction::invokeBlock ...")
    val maybeUsername = request.session.get(SESSION_USERNAME_KEY)
    maybeUsername match {
      case None => {
        Future.successful(
          Results.Redirect("/login")
        )
      }
      case Some(u) => {
        val res: Future[Result] = block(request)
        res
      }
    }
  }
}

