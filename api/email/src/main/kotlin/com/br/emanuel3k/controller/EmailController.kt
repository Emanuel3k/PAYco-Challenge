package com.br.emanuel3k.controller

import com.br.emanuel3k.dto.EmailForm
import com.br.emanuel3k.mapper.EmailFormMapper
import com.br.emanuel3k.model.Email
import com.br.emanuel3k.service.EmailService
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.validation.Valid
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import java.net.URI


@Path("api/email")
class EmailController {

    @Inject
    private lateinit var emailService: EmailService

    @Inject
    private lateinit var emailFormMapper: EmailFormMapper

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
    fun postEmail(@Valid emailForm: EmailForm): Uni<Response> {
        return emailService.postEmail(emailForm).onItem().transform { e ->
            emitter.send(e)
            URI.create("${e.id}")
        }.onItem().transform { uri ->
            Response.created(uri).build()
        }
    }

    @Incoming("emails")
    fun updateEmail(id: String) {
        emailService.updateEmail(id.toLong())
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