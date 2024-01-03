package com.br.emanuel3k.controller

import com.br.emanuel3k.model.Email
import com.br.emanuel3k.service.EmailService
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.eclipse.microprofile.reactive.messaging.Incoming
import java.net.URI


@Path("api/email")
class EmailController {

    @Inject
    private lateinit var emailService: EmailService

    @Channel("process-email")
    private lateinit var emitter: Emitter<Email>

    @GET
    fun getAllEmail(): Multi<Email> {
        return emailService.getAllEmail()
    }

    @GET
    @Path("/{id}")
    fun findById(@PathParam("id") id: Long): Uni<Response> {
        return emailService.findById(id).onItem().transform { e ->
            if (e != null) Response.ok(e)
            else Response.status(Response.Status.NOT_FOUND)
        }.onItem().transform(Response.ResponseBuilder::build)
    }

    @POST
    fun postEmail(@Valid email: Email): Uni<Response> {
        return emailService.postEmail(email).onItem().transform { id ->
            URI.create("$id")
        }.onItem().transform { uri ->
            emitter.send(email)
            Response.created(uri).build()
        }
    }

    /*@DELETE
    @Path("/{id}")
    fun deleteEmail(@PathParam("id") id: Long): Uni<Response> {
        return emailService.deleteEmail(id).onItem().transform { e ->
            if (e) Response.status(Response.Status.NO_CONTENT)
            else Response.status(Response.Status.NOT_FOUND)
        }.onItem().transform(Response.ResponseBuilder::build)
    }*/
}