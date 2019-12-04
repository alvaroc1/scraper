import org.scalatestplus.selenium.WebBrowser
import org.scalatestplus.selenium.Chrome
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver

object scraper extends App with Chrome {
  //implicit val webDriver: WebDriver = new HtmlUnitDriver

  go to ("https://glassdor.com")

  println("Running scraper")
}

