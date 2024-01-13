package br.com.emanuel3k.service

import br.com.emanuel3k.model.Email
import com.google.gson.Gson
import io.quarkus.mailer.Mail
import io.quarkus.mailer.reactive.ReactiveMailer
import io.smallrye.mutiny.Uni
import io.vertx.core.json.JsonObject
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Outgoing
import java.util.*

@ApplicationScoped
class EmailService(
    private val mailer: ReactiveMailer,
) {

    @Incoming("requests")
    @Outgoing("emails")
    fun sendEmail(event: JsonObject): Uni<UUID> {
        val eventEmail = Gson().fromJson(event.toString(), Email::class.java)

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