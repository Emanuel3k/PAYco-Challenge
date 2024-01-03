package com.br.emanuel3k.repository

import com.br.emanuel3k.model.Email
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import io.vertx.sqlclient.Row
import jakarta.validation.Valid

interface EmailRepository {
    fun from(row: Row): Email
    fun getAllEmail(): Multi<Email>
    fun findById(id: Long): Uni<Email>
    fun postEmail(@Valid email: Email): Uni<Long>
    fun updateEmail(id: Long): Uni<Long>
//    fun deleteEmail(id: Long): Uni<Boolean>
}