package com.opalynskyi.module_injector

interface ComponentHolder<C : BaseAPI, D : BaseDependencies> {

    fun init(dependencies: D)

    fun get(): C

    fun reset()
}

interface BaseDependencies

interface BaseAPI
