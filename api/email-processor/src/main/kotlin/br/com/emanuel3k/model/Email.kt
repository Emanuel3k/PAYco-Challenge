package br.com.emanuel3k.model

import io.quarkus.runtime.annotations.RegisterForReflection
import java.util.*

@RegisterForReflection
data class Email(
    val id: UUID,
    val sender: String,
    val recipient: String,
    val subject: String,
    val body: String,
    val delivered: Boolean,
)