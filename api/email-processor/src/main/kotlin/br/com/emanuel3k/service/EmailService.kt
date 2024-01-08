package br.com.emanuel3k.service

import br.com.emanuel3k.model.Email
import com.google.gson.Gson
import io.quarkus.mailer.Mail
import io.quarkus.mailer.reactive.ReactiveMailer
import io.smallrye.mutiny.Uni
import io.vertx.core.json.JsonObject
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty

@ApplicationScoped
class EmailService {

    @Inject
    private lateinit var mailer: ReactiveMailer

    fun sendEmail(event: JsonObject): Uni<Long> {
        val eventEmail = Gson().fromJson(event.toString(), Email::class.java)

        eventEmail.sender

        return mailer.send(
            Mail.withText(
                eventEmail.recipient,
                eventEmail.subject,
                eventEmail.body
            )
        ).onItem().transform {
            eventEmail.id
        }
    }
}