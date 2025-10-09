package dev.ng5m.util

import dev.ng5m.serialization.nbt.Tag
import dev.ng5m.serialization.nbt.impl.CompoundTag
import dev.ng5m.serialization.nbt.NBT
import dev.ng5m.util.annotation.Serialize
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.jvm.javaField

fun <T> toNBT(instance: T): CompoundTag {
    val ct = CompoundTag()

    instance!!::class.declaredMemberProperties
        .filter { it.hasAnnotation<Serialize>() }
        .forEach {
            @Suppress("UNCHECKED_CAST")
            val tag = NBT.toNBT((it as KProperty1<Any, *>).get(instance))
            tag.name = NBT.getRealName(it.javaField)

            ct.add(tag)
        }

    return ct
}