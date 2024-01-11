package com.br.emanuel3k.service

import com.br.emanuel3k.dto.EmailForm
import com.br.emanuel3k.mapper.EmailFormMapper
import com.br.emanuel3k.model.Email
import com.br.emanuel3k.repository.EmailRepository
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.validation.Valid
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.eclipse.microprofile.reactive.messaging.Incoming


@ApplicationScoped
class EmailService(
    @Channel("process-email")
    private val emitter: Emitter<Email>,
    private val emailFormMapper: EmailFormMapper,
) : EmailRepository {

    /*Por algum motivo a conexão com o banco é fechada se não tiver o return sem finalizar a ação, e se tiver alguma
    ação sendo feita antes do return tb, então resolvi isso com a anotação @WithTransaction*/

    @WithTransaction
    override fun getAll(): Uni<List<Email>> {
        return this.listAll()
    }

    @WithTransaction
    override fun findById(id: String): Uni<Email?> {
        return this.find("id", id).firstResult()
    }

    @WithTransaction
    override fun create(@Valid emailForm: EmailForm): Uni<String> {
        val email = emailFormMapper.map(emailForm)
        return this.persist(email).onItem().transform {
            println(email.id)
            emitter.send(email)
            email.id
        }
    }


    @Incoming("emails")
    @WithTransaction
    override fun update(id: String): Uni<Email?> {
        val email = findById(id)

        return email.onItem().transform { e ->
            if (e != null) {
                e.delivered = true
                this.persistAndFlush(e)
                e
            } else e
        }
    }


    // METHOD WITH PROBLEM
    /* @WithTransaction
     fun deleteById(id: UUID): Uni<Void>? {
         val email = findById(id)

         return email.onItem().transformToUni { e ->
             e?.let { this.delete(it) }
         }
     }*/
}