package com.example.anidiff_jvm.comparison

import com.example.anidiff_jvm.entities.Entity

fun <T : Entity> compareEntityLists(list1: List<T>, list2: List<T>): List<T> {
    val diff1 = getDiff(list1, list2)
    val diff2 = getDiff(list2, list1)

    return (diff1 + diff2).distinctBy { it.id }
}

private fun <T : Entity> getDiff(list1: List<T>, list2: List<T>): List<T> {
    val map2 = list2.associateBy { it.id }

    return list1.mapNotNull { compare(it, map2) }
}

private fun <T : Entity> compare(entity: T, entities: Map<Int, T>): T? {
    val otherEntity = entities[entity.id]

    return if (entity.isEqual(otherEntity)) null else entity
}
