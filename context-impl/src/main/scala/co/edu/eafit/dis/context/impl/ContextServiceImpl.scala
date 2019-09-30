package co.edu.eafit.dis.context.impl

import akka.{Done, NotUsed}
import co.edu.eafit.dis.context.api.{ContextRegistry, ContextService}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry

/**
  * Implementation of the [[ContextService]].
  */
class ContextServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends ContextService {

  override def saveContextRegistry(id: String): ServiceCall[ContextRegistry, Done] = { ctx =>
    val ref = persistentEntityRegistry.refFor[ContextEntity](id)

    ref.ask(SaveContextRegistry(ctx))
  }

  override def getContextObject(id: String): ServiceCall[NotUsed, List[ContextRegistry]] = { _ =>
    val ref = persistentEntityRegistry.refFor[ContextEntity](id)

    ref.ask(GetContentObject(id))
  }
}