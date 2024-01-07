package com.br.emanuel3k.mapper

interface Mapper<T, U> {
    fun map(t: T): U
}