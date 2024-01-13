package com.br.emanuel3k.repository

import com.br.emanuel3k.dto.EmailForm
import com.br.emanuel3k.model.Email
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.validation.Valid
import jakarta.ws.rs.core.Response
import java.util.*

@ApplicationScoped
interface EmailRepository : PanacheRepository<Email> {
    fun getAll(): Uni<List<Email>>
    fun findById(id: UUID): Uni<Response>
    fun create(@Valid emailForm: EmailForm): Uni<Response>
    fun update(id: String): Uni<Email>
//    fun deleteById(id: UUID): Uni<Response>
}