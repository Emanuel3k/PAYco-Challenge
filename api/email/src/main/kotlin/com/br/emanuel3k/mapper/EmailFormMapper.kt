package com.br.emanuel3k.mapper

import com.br.emanuel3k.dto.EmailForm
import com.br.emanuel3k.model.Email
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class EmailFormMapper : Mapper<EmailForm, Email> {

    override fun map(t: EmailForm): Email {
        val email = Email()
        email.sender = t.sender
        email.recipient = t.recipient
        email.subject = t.subject
        email.body = t.body

        return email
    }

}