package controllers

import models.Items
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

class Shop extends Controller {

  var items = Items(20, 10)

  def listItems = Action {
    Ok(views.html.listItems(items, Shop.sellItemsForm))
  }

  def sellItems = Action { implicit request =>
    val boughtItems = Shop.sellItemsForm.bindFromRequest
    boughtItems.fold({ formWithErrors =>
      BadRequest(views.html.listItems(items, Shop.sellItemsForm))
    }, { item =>
      items = Items(items.itemA - item.itemA, items.itemB - item.itemB)
      Redirect(routes.Shop.listItems)
    })
  }
}

object Shop {
  val sellItemsForm = Form(
    mapping (
      "itemA" -> number,
      "itemB" -> number
    )(Items.apply)(Items.unapply)
  )
}
