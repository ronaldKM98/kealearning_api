package co.edu.eafit.dis.context.api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json.{Format, Json}

object ContextService {
  val TOPIC_NAME = "context"
}

/**
  * The context service interface.
  * <p>
  * This describes everything that Lagom needs to know about how to serve and
  * consume the ContextService.
  */
trait ContextService extends Service {
  /**
    * Example: curl http://localhost:9000/api/context/:id/save
    */
  def saveContextRegistry(id: String): ServiceCall[ContextRegistry, Done]

  def getContextObject(id: String): ServiceCall[NotUsed, List[ContextRegistry]]

  override final def descriptor: Descriptor = {
    import Service._
    // @formatter:off
    named("/context")
      .withCalls(
        restCall(method = Method.POST, pathPattern = "/context/:id/save", saveContextRegistry _),
        restCall(method = Method.GET, pathPattern = "/context/:id", getContextObject _)
      )
      .withAutoAcl(autoAcl = true)
    // @formatter:on
  }
}

/**
  * The context object class.
  */
case class ContextRegistry(id: String, message: String)

object ContextRegistry {
  /**
    * Format for converting context objects to and from JSON.
    *
    * This will be picked up by a Lagom implicit conversion from Play's JSON format to Lagom's message serializer.
    */
  implicit val format: Format[ContextRegistry] = Json.format
}