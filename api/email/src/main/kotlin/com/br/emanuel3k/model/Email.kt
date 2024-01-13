package com.br.emanuel3k.model

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import io.quarkus.runtime.annotations.RegisterForReflection
import jakarta.persistence.*
import java.util.*


@Entity
@RegisterForReflection
class Email : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

    lateinit var recipient: String

    lateinit var subject: String

    @Column(columnDefinition = "TEXT")
    lateinit var body: String

    var delivered: Boolean = false
}