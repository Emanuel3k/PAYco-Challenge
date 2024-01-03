package com.br.emanuel3k.model

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntity
import io.quarkus.runtime.annotations.RegisterForReflection
import jakarta.persistence.Cacheable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size


@Entity
@Cacheable
@RegisterForReflection
class Email : PanacheEntity() {

    @Column
    @field:NotEmpty
    lateinit var sender: String

    @Column
    @field:NotEmpty
    lateinit var recipient: String

    @Column
    @field:NotEmpty
    @field:Size(min = 10)
    lateinit var subject: String

    @Column
    @field:NotEmpty
    @field:Size(min = 5)
    lateinit var body: String

    @Column
    var delivered: Boolean = false
}