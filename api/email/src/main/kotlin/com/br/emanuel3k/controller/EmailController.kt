package com.br.emanuel3k.controller

import com.br.emanuel3k.dto.EmailForm
import com.br.emanuel3k.model.Email
import com.br.emanuel3k.service.EmailService
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.validation.Valid
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.core.Response
import java.util.*


@ApplicationScoped
@Path("api/emails")
class EmailController(
    private var emailService: EmailService,
) {
    @GET
    fun getAll(): Uni<List<Email>> {
        return emailService.getAll()
    }

    @GET
    @Path("/id/{id}")
    fun findById(@PathParam("id") id: UUID): Uni<Response> {
        return try {
            emailService.findById(id)
        } catch (e: Exception) {
            Uni.createFrom().item {
                Response.serverError().entity(e).build()
            }
        }
    }

    @POST
    fun create(@Valid emailForm: EmailForm): Uni<Response> {
        return try {
            emailService.create(emailForm)
        } catch (e: Exception) {
            Uni.createFrom().item {
                Response.serverError().entity(e).build()
            }
        }
    }

    /*@DELETE
    @Path("/{id}")
    fun delete(@PathParam("id") id: UUID): Uni<Response> {
        return emailService.deleteById(id).onItem().transform {
            Response.noContent().build()
        }
    }*/

}

