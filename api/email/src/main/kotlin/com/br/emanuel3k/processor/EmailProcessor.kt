package com.br.emanuel3k.processor

import com.br.emanuel3k.service.EmailService
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.reactive.messaging.Incoming

@ApplicationScoped
class EmailProcessor {

    @Inject
    private lateinit var emailService: EmailService

    @Incoming("emails")
    fun updateEmail(id: String) {
        emailService.updateEmail(id.toLong())
    }
}