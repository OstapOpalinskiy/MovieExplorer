package com.opalynskyi.movies_core.domain.entities

/**
Workaround to avoid paging library dependency in domain layer
 */
abstract class MoviePage {
    abstract fun getPage(): Any
}