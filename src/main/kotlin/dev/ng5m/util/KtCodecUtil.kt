package dev.ng5m.util

import dev.ng5m.serialization.Codec
import org.joml.Vector3d
import org.joml.Vector3f
import org.joml.Vector3i
import java.util.Optional
import kotlin.jvm.optionals.getOrNull
import kotlin.reflect.KClass

val CODEC_VECTOR3DS: Codec<Vector3d> = Codec.of(
    Codec.SHORT, { it.x.toInt().toShort() },
    Codec.SHORT, { it.y.toInt().toShort() },
    Codec.SHORT, { it.z.toInt().toShort() },
    { x, y, z -> Vector3d(x.toDouble(), y.toDouble(), z.toDouble()) }
)
val CODEC_VECTOR3F: Codec<Vector3f> = Codec.of(
    Codec.FLOAT, { it.x },
    Codec.FLOAT, { it.y },
    Codec.FLOAT, { it.z },
    ::Vector3f
)
val CODEC_VECTOR3D: Codec<Vector3d> = Codec.of(
    Codec.DOUBLE, { it.x },
    Codec.DOUBLE, { it.y },
    Codec.DOUBLE, { it.z },
    ::Vector3d
)

val CODEC_POSITION: Codec<Vector3i> = Codec.of(
    { buf ->
        val l = buf.readLong()
        Vector3i((l shr 38).toInt(), (l shl 52 shr 52).toInt(), (l shl 26 shr 38).toInt())
    },
    { buf, vec ->
        buf.writeLong(((vec.x.toLong() and 0x3FFFFFFL) shl 38) or ((vec.z.toLong() and 0x3FFFFFFL) shl 12) or (vec.y.toLong() and 0xFFFL))
    }
)

fun Vector3d.of(x: Long, y: Long, z: Long): Vector3d = Vector3d(x.toDouble(), y.toDouble(), z.toDouble())
fun Vector3d.copy(): Vector3d = Vector3d(x, y, z)
inline fun Vector3d.transform(crossinline transform: (Double) -> Double) =
    Vector3d(transform(x), transform(y), transform(z))
fun Vector3d.toInts(): Vector3i = Vector3i(x.toInt(), y.toInt(), z.toInt())
fun Vector3f.toInts(): Vector3i = Vector3i(x.toInt(), y.toInt(), z.toInt())
fun Vector3i.toDoubles(): Vector3d = Vector3d(x.toDouble(), y.toDouble(), z.toDouble())

fun <T : Any> Codec<T>.nullable(): Codec<T?> = this.prefixedOptional().xmap<T>(
    { it.getOrNull() }, { Optional.ofNullable(it) }
)

inline fun <reified E : Enum<E>> ofEnum(): Codec<E> = Codec.ofEnum(E::class.java)

fun <T : Any> Codec<T>.forType(kClass: KClass<T>): Codec<T> = this.forType(kClass.java)