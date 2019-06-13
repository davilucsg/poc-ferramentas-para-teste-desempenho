package albums

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.util.parsing.json.JSONObject

class Album extends Simulation {

val feeder = Iterator.continually(Map(("userIdGerado", scala.util.Random.nextInt(5000))))

  val headers = Map(
    "Content-Type" -> "application/json"
  )

  val httpConfiguration = http.baseUrl("https://jsonplaceholder.typicode.com/")
    .headers(headers)

  val postPassengerCenarioCarga = scenario("POC Gatling")
    .feed(feeder)
    .exec(
      http("Create Albums")
        .post("albums")
        .body(StringBody(
          JSONObject.apply(
            Map(
                "userId"->"${userIdGerado}",
                "title"-> "Album de fotos do Bicampeonato do clube Atlético Mineiro"
               )
          ).toString()
        ))
        .check(jsonPath("$.id").saveAs("id"))
    )
    .exec(
      http("Delete Albums")
        .delete("albums/${id}")
         )
  
  setUp(
    postPassengerCenarioCarga.inject(
      rampUsers(50) during (1 minutes)
    )
  ).protocols(httpConfiguration)
    .maxDuration(2 minutes)
}