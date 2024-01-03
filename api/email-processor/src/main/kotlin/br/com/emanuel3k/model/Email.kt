package br.com.emanuel3k.model

@RegisterForReflection
data class Email(
    val id: Long,
    val sender: String,
    val recipient: String,
    val subject: String,
    val body: String,
    val delivered: Boolean,
)