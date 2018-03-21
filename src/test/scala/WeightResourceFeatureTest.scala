import com.twitter.finagle.http.Status
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

class WeightResourceFeatureTest extends FeatureTest {
  override protected def server: EmbeddedHttpServer = new EmbeddedHttpServer(
    twitterServer = new FitmanServer
  )

  "WeightResource" should {
    "Save user weight when Post request is made" in {
      server.httpPost(
        path = "/weights",
        postBody =
          """
            |{
            |"user":"thomas",
            |"weight":85,
            |"status":"Feeling great!!!"
            |}
          """.stripMargin,
        andExpect = Status.Created,
        withLocation = "/weights/thomas"
      )
    }

    "List all weights for a user when GET request is made" in {
      val response = server.httpPost(
        path = "/weights",
        postBody =
          """
            |{
            |"user" : "test_user_1",
            |"weight" : 80,
            |"status" : "2016-01-03T14:34:06.871Z"
            |}
          """.stripMargin,
        andExpect = Status.Created
      )

      server.httpGetJson[List[Weight]](
        path = response.location.get,
        andExpect = Status.Ok,
        withJsonBody =
          """
            |[
            |  {
            |    "user" : "test_user_1",
            |    "weight" : 80,
            |    "status" : "2016-01-03T14:34:06.871Z"
            |  }
            |]
          """.stripMargin
      )
    }
  }
}
