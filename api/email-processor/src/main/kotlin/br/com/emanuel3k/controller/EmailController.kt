package br.com.emanuel3k.controller

import br.com.emanuel3k.service.EmailService
import io.smallrye.mutiny.Uni
import io.vertx.core.json.JsonObject
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Outgoing


@ApplicationScoped
class EmailController {

    @Inject
    private lateinit var emailService: EmailService

    @Incoming("requests")
    @Outgoing("emails")
    fun process(event: JsonObject): Uni<Long> {
        Thread.sleep(5000)
        return emailService.sendEmail(event)
    }
}