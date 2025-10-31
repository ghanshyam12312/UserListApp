package ghanshyam.demo.anzusersapp.core.mappers

import kotlin.collections.mapNotNull

interface BaseMappers<Model, Entity> {
    fun mapFromModel(model: Model): Entity
    fun mapList(models: List<Model>): List<Entity> = models.mapNotNull { mapFromModel(it) }
}