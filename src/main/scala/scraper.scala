import org.scalatestplus.selenium.WebBrowser
import org.scalatestplus.selenium.Chrome
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.{By, Keys}
import org.scalatest.time.Span
import org.scalatest.time.Seconds
import java.io.{File, PrintWriter}

object scraper extends App with Chrome with Utils {
  implicitlyWait(Span(3, Seconds))

  // create new file
  // write csv header
  results.write("companyName,jobTitle,location\n")

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

  // set company size filter
  closePopupIfOneIsOpen
  click on className("more")
  click on xpath("(//*[@data-test='SIZE'])[2]") // there's two, we want the second
  click on xpath("//*[@class='flyout']//*[./text()='1-200 employees']")

  closePopupIfOneIsOpen

  readJobs

  var page = 2
  // if we can find a next page, follow it
  while (find(pageLink(page)).nonEmpty) {
    click on dealWithOverlay(pageLink(page))

    readJobs

    closePopupIfOneIsOpen

    page = page + 1
  }

  // close the file
  results.close()
  
  println("DONE")
}

trait Utils {self: Chrome =>
  // results will go here
  val results = new PrintWriter(new File("results.csv"))

  // if some popup is open, close it
  def closePopupIfOneIsOpen = {
    Thread.sleep(1000) // make sure popup has time to load
    find(className("modal_closeIcon")).foreach {closePopupButton =>
      click on closePopupButton
      Thread.sleep(1000) // allow overlay to clear
    }
    find(xpath("//*[@data-test='closeProfileTooltip']")).foreach {closeProfilePopupButton =>
      click on closeProfilePopupButton
      Thread.sleep(500)
    }
  }

  // find the link for a page (ex: 1, 2, 3, etc)
  def pageLink (num: Int) = {
    xpath(s"//a[./text()=$num]")
  }

  def readJobs = {
    // for each job
    findAll(xpath("//li[@class='jl']")).foreach {element =>
      // query starting from this element
      // this requires the java-api
      val companyName = element.underlying.findElement(By.xpath("//*[@class='jobHeader']/a/div")).getText()
      val jobTitle = element.attribute("data-normalize-job-title").get
      val location = element.attribute("data-job-loc").get

      println(s"$companyName,$location,$jobTitle")

      results.write(s"$companyName,$location,$jobTitle\n")
    }
  }

  def dealWithOverlay (query: Query) = {
    Thread.sleep(1000)
    closePopupIfOneIsOpen
    Thread.sleep(1000)
    query
  }
}
