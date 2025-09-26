package dev.ng5m.util.json

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import dev.ng5m.serialization.util.Either
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class EitherTypeAdapterFactory : TypeAdapterFactory {

    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        if (type.rawType != Either::class.java) return null

        val parameterizedType = type.type as? ParameterizedType
            ?: throw IllegalArgumentException("Either must be parameterized")

        val typeArguments: Array<Type> = parameterizedType.actualTypeArguments
        if (typeArguments.size < 2)
            throw IllegalArgumentException("Either requires 2 type parameters")

        val leftType = typeArguments[0]
        val rightType = typeArguments[1]

        val adapter = EitherTypeAdapter<Any, Any>(gson, leftType, rightType)

        @Suppress("UNCHECKED_CAST")
        return adapter as TypeAdapter<T>
    }
}

class EitherTypeAdapter<L, R>(
    private val gson: Gson,
    private val leftType: Type,
    private val rightType: Type
) : TypeAdapter<Either<L, R>>() {

    override fun write(out: JsonWriter, value: Either<L, R>?) {
        if (value == null) {
            out.nullValue()
            return
        }

        when {
            value.isLeft -> gson.toJson(value.left, leftType, out)
            value.isRight -> gson.toJson(value.right, rightType, out)
            else -> out.nullValue()
        }
    }

    override fun read(`in`: JsonReader): Either<L, R>? {
        val jsonElement: JsonElement = JsonParser.parseReader(`in`)

        if (jsonElement.isJsonNull) {
            return null
        }

        return try {
            val rightValue: R = gson.fromJson(jsonElement, rightType)
            Either.ofRight(rightValue)
        } catch (rightException: Exception) {
            try {
                val leftValue: L = gson.fromJson(jsonElement, leftType)
                Either.ofLeft(leftValue)
            } catch (leftException: Exception) {
                throw IOException(
                    "Failed to deserialize Either: Cannot convert JSON to either $leftType or $rightType. " +
                            "Left error: ${leftException.message}, Right error: ${rightException.message}"
                )
            }
        }
    }
}