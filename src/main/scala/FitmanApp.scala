import com.twitter.finagle.http.Request
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.{Controller, HttpServer}

object FitmanServerMain extends FitmanServer

class FitmanServer extends HttpServer {

  override protected def defaultFinatraHttpPort: String = ":8080"
  override protected def defaultTracingEnabled: Boolean = false
  override protected def defaultHttpServerName: String = "FitMan"

  override protected def configureHttp(router: HttpRouter): Unit = {
    router.add(new HelloController)
    router.add(new WeightResource)
  }
}

class HelloController extends Controller {

  get("/hello") { request: Request => // Response that can be converted to http.Response
    "Fitman says hello"
  }

}