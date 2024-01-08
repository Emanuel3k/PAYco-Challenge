package com.br.emanuel3k.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class EmailForm(
    @field:NotEmpty
    @field:Email val sender: String,

    @field:NotEmpty
    @field:Email val recipient: String,

    @field:NotEmpty
    @field:Size(min = 10) val subject: String,

    @field:NotEmpty
    @field:Size(min = 5) val body: String,
)