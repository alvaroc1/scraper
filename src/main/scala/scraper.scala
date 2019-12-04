import org.scalatestplus.selenium.WebBrowser
import org.scalatestplus.selenium.Chrome
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.Keys
import org.scalatest.time.Span
import org.scalatest.time.Seconds

object scraper extends App with Chrome {
  
  implicitlyWait(Span(10, Seconds))

  go to ("https://glassdor.com")

  click on linkText("Sign In") 

  click on emailField("username")

  emailField("username").underlying.sendKeys("trenton@wealthcademy.com")

  click on pwdField("password")

  enter("jobhunt1")

  //click on "Sign In"
  submit()

  //click on ".SVGInline modal_closeIcon"

  click on textField("sc.keyword")

  enter("vp of engineering")

  textField("sc.keyword").underlying.sendKeys(Keys.ENTER)

  println("Running scraper")
}

