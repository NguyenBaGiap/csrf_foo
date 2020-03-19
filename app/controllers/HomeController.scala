package controllers

import javax.inject._
import models.Social
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

@Singleton
class HomeController @Inject()(
                                sercured:AuthenticatedUserAction,
                                cc: MessagesControllerComponents
                              ) extends MessagesAbstractController(cc) {

  // form login
  val form:Form[UserLogin] = Form(
    mapping(
      "email" -> nonEmptyText
    )(UserLogin.apply)(UserLogin.unapply)
  )

  private val postsLoginUrl = routes.HomeController.verify()

  // form share
  val formShare:Form[UserShare] = Form(
    mapping(
      "share" -> nonEmptyText
    )(UserShare.apply)(UserShare.unapply)
  )

  private val sharePost = routes.HomeController.postShare()

  // ....

  private val SESSION_USERNAME_KEY = "username"

  def index = sercured { implicit request =>
    val content = Social.findAll()

    Ok(views.html.index(content,formShare,sharePost))
  }

  def login = Action { implicit request: MessagesRequest[AnyContent] =>
    if(request.session.get(SESSION_USERNAME_KEY).isDefined) Redirect("/")
     else Ok(views.html.login(form,postsLoginUrl))
  }

  def verify = Action { implicit request =>
    val email = request.body.asFormUrlEncoded.get("email").head
    Social.verify(email) match {
      case Some(s) => Redirect("/").withSession(SESSION_USERNAME_KEY -> s.email)
      case None =>
        val id =  Social.createWithAttributes('email -> email)
        Social.findById(id)
          .map(s => Redirect("/").withSession(SESSION_USERNAME_KEY -> s.email))
          .getOrElse(Redirect("/login"))
    }
  }

  def logout = sercured { implicit request =>
    Redirect(routes.HomeController.login())
      .withNewSession
  }

  def postShare() = sercured {implicit request =>
    val share = request.body.asFormUrlEncoded.get("share").head
    val email = request.session.get(SESSION_USERNAME_KEY).get
    Social.createWithAttributes(
      'email -> email,
      'share -> share
    )
    Redirect("/")
  }

}
