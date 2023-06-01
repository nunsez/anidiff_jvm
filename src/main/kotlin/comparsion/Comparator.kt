package com.example.anidiff_jvm.comparsion

import com.example.anidiff_jvm.entities.Entity

class Comparator(
    private val malList: List<Entity>,
    private val shikiList: List<Entity>
) {
    fun compare(): List<Entity> {
        val shiki = toMap(shikiList)
        val malDiff = malList.mapNotNull { compare(it, shiki) }

        val mal = toMap(malList)
        val shikiDiff = malList.mapNotNull { compare(it, mal) }

        return (malDiff + shikiDiff).distinctBy { it.id }
    }

    private fun compare(entity: Entity, entities: Map<Int, Entity>): Entity? {
        val otherEntity = entities[entity.id]
        return if (entity == otherEntity) null else entity
    }

    private fun toMap(list: List<Entity>): Map<Int, Entity> {
        return list.associateBy { it.id }
    }
}
