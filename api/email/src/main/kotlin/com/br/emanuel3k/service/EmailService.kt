package com.br.emanuel3k.service

import com.br.emanuel3k.model.Email
import com.br.emanuel3k.repository.EmailRepository
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.Tuple
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.validation.Valid


@ApplicationScoped
class EmailService : EmailRepository {

    companion object {
        var id: Long = 0
    }

    @Inject
    private lateinit var pgPool: PgPool

    override fun from(row: Row): Email {
        val email = Email()
        email.id = row.getLong("id")
        email.sender = row.getString("sender")
        email.recipient = row.getString("recipient")
        email.subject = row.getString("subject")
        email.body = row.getString("body")
        email.delivered = row.getBoolean("delivered")
        return email
    }

    override fun getAllEmail(): Multi<Email> {
        val future = pgPool.preparedQuery("select * from email").execute()
        val uni = Uni.createFrom().completionStage(future.toCompletionStage())

        return uni.onItem()
            .transformToMulti { set ->
                Multi.createFrom().iterable(set)
            }.onItem().transform { row ->
                from(row)
            }
    }

    override fun findById(id: Long): Uni<Email> {
        val future = pgPool.preparedQuery("select * from email where id = $1")
            .execute(Tuple.of(id))
        val uni = Uni.createFrom().completionStage(future.toCompletionStage())

        return uni.onItem()
            .transform { rows ->
                if (rows.iterator().hasNext()) from(rows.iterator().next())
                else null
            }
    }

    override fun postEmail(@Valid email: Email): Uni<Long> {
        email.id = id++

        val future =
            pgPool.preparedQuery(
                "INSERT INTO email (id, sender, recipient, subject, body, delivered) VALUES ($1, $2, $3, $4, $5, $6) " +
                        "RETURNING id"
            ).execute(Tuple.of(email.id, email.sender, email.recipient, email.subject, email.body, email.delivered))
        val uni = Uni.createFrom().completionStage(future.toCompletionStage())

        return uni.onItem().transform { set ->
            set.iterator().next().getLong("id")
        }
    }

    override fun updateEmail(id: Long): Uni<Long> {
        val future = pgPool.preparedQuery("UPDATE email SET delivered = $1 WHERE id=$2 RETURNING id")
            .execute(Tuple.of(true, id))
        val uni = Uni.createFrom().completionStage(future.toCompletionStage())

        return uni.onItem().transform { set ->
            set.iterator().next().getLong("id")
        }
    }

    /*fun deleteEmail(id: Long): Uni<Boolean> {
        val future = pgPool.preparedQuery("DELETE FROM email WHERE id = $1")
            .execute(Tuple.of(id))
        val uni = Uni.createFrom().completionStage(future.toCompletionStage())

        return uni.onItem().transform { set ->
            set.rowCount() == 1
        }
    }*/


}
