package br.com.emanuel3k.model

import io.quarkus.runtime.annotations.RegisterForReflection

@RegisterForReflection
data class Email(
    val id: String,
    val sender: String,
    val recipient: String,
    val subject: String,
    val body: String,
    val delivered: Boolean,
)