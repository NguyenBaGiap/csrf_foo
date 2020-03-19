import javax.inject.Singleton
import play.api.http.HttpErrorHandler
import play.api.mvc.RequestHeader
import play.api.mvc.Results.{InternalServerError, Ok, Redirect}

import scala.concurrent.Future

@Singleton
class ErrorHandler extends HttpErrorHandler {
  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {

    Future.successful(
      if(request.session.get("username").isDefined){
        Redirect("/")
      } else {
        Redirect("/login")
      }
    )
  }

  def onServerError(request: RequestHeader, exception: Throwable) = {
    Future.successful(
      Redirect("/login")
    )
  }
}
