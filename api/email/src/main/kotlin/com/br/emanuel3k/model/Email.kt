package com.br.emanuel3k.model

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*


@Entity
@Table
class Email : PanacheEntityBase {

    companion object {
        var autoId: Long = 0
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id: Long = autoId++

    @Column
    lateinit var sender: String

    @Column
    lateinit var recipient: String

    @Column
    lateinit var subject: String

    @Column(columnDefinition = "TEXT")
    lateinit var body: String

    @Column
    var delivered: Boolean = false
}