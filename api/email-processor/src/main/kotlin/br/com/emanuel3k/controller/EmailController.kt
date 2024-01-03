package br.com.emanuel3k.controller

import br.com.emanuel3k.model.Email
import com.google.gson.Gson
import io.smallrye.reactive.messaging.annotations.Blocking
import io.vertx.core.json.JsonObject
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Outgoing

@ApplicationScoped
class EmailController {

    @Incoming("requests")
    @Outgoing("emails")
    @Blocking
    fun process(event: JsonObject): Long {
        Thread.sleep(8000)
        val eventEmail = Gson().fromJson(event.toString(), Email::class.java)
        return eventEmail.id
    }
}