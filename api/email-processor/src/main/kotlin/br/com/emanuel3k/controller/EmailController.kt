package br.com.emanuel3k.controller

import br.com.emanuel3k.service.EmailService
import io.smallrye.mutiny.Uni
import io.vertx.core.json.JsonObject
import jakarta.enterprise.context.ApplicationScoped
import java.util.*


@ApplicationScoped
class EmailController(
    private var emailService: EmailService,
) {

    fun process(event: JsonObject): Uni<String> {
        return emailService.sendEmail(event)
    }
}