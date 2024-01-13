package com.br.emanuel3k.service

import com.br.emanuel3k.dto.EmailForm
import com.br.emanuel3k.mapper.EmailFormMapper
import com.br.emanuel3k.model.Email
import com.br.emanuel3k.repository.EmailRepository
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.validation.Valid
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.eclipse.microprofile.reactive.messaging.Incoming
import java.net.URI
import java.util.*


@ApplicationScoped
class EmailService(
    @Channel("process-email")
    private val emitter: Emitter<Email>,
    private val emailFormMapper: EmailFormMapper,
) : EmailRepository {

    override fun getAll(): Uni<List<Email>> {
        return this.listAll()
    }

    override fun findById(id: UUID): Uni<Response> {
        return this.find("id", id).firstResult()
            .onItem().transform { e ->
                e?.let { Response.ok(it).build() }
                    ?: Response.status(Response.Status.NOT_FOUND)
                        .entity("Email with id: $id not exists").build()
            }
    }

    @WithTransaction
    override fun create(@Valid emailForm: EmailForm): Uni<Response> {
        val email = emailFormMapper.map(emailForm)
        return this.persist(email).onItem().transform {
            emitter.send(email)
            URI.create("/api/emails/${email.id}")
        }.onItem().transform { uri ->
            Response.created(uri).build()
        }
    }

    @Incoming("emails")
    @WithTransaction
    override fun update(id: String): Uni<Email> {
        return find("id", UUID.fromString(id))
            .firstResult().onItem().transformToUni { e ->
                e?.let {
                    e.delivered = true
                    this.persistAndFlush(e)
                }
            }
    }

    /* @WithTransaction
     fun deleteById(id: UUID): Uni<Void>? {
         val email = findById(id)

         return email.onItem().transformToUni { e ->
             e?.let { this.delete(it) }
         }
     }*/
}