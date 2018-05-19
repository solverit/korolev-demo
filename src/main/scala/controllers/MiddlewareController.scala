package controllers

import models.{ApplicationInformation, ApplicationStatus}


class MiddlewareController {

  def health: ApplicationStatus = ApplicationStatus(status = true, name = "korolev-demo")

  def info: ApplicationInformation = ApplicationInformation(version = "0.0.1", name = "korolev-demo")

}
