import org.scalatestplus.selenium.WebBrowser
import org.scalatestplus.selenium.Chrome
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.Keys
import org.scalatest.time.Span
import org.scalatest.time.Seconds

object scraper extends App with Chrome {

  implicitlyWait(Span(2, Seconds))

  // if some popup is open, close it
  def closePopupIfOneIsOpen = {
    find(".SVGInline modal_closeIcon").foreach {closePopupButton =>
      click on closePopupButton
    }
  }

  // find the link for a page (ex: 1, 2, 3, etc)
  def pageLink (num: Int) = {
    xpath(s"//a[./text()=$num]")
  }

  go to ("https://glassdor.com")

  click on linkText("Sign In") 

  click on emailField("username")
  pressKeys("trenton@wealthcademy.com")

  click on pwdField("password")
  enter("jobhunt1")

  submit()

  closePopupIfOneIsOpen

  click on textField("sc.keyword")
  enter("vp of engineering")

  // clear location field
  click on textField("sc.location")
  enter("") // clear
  pressKeys(" ") // force field to register change
  pressKeys(Keys.ENTER.toString)

  Thread.sleep(1000)

  closePopupIfOneIsOpen

  // for each job
  findAll(xpath("//li[@class='jl']")).foreach {element =>
    println(element.attribute("data-normalize-job-title").get)
  }

  /*
  click on pageLink(2)

  closePopupIfOneIsOpen

  click on pageLink(3)

  closePopupIfOneIsOpen
  */

  println("Running scraper")
}

