package com.opalynskyi.cleanmovies.core.data

interface EntityMapper<E, D> {

    fun mapFromEntity(entity: E): D

    fun mapToEntity(domain: D): E
}