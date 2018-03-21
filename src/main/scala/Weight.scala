import java.time.Instant

case class Weight (
                  user: String,
                  weight: Int,
                  status: Option[String],
                  postedAt: Instant = Instant.now()
                  )
