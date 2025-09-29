package dev.ng5m.data.json

import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import dev.ng5m.util.IntProvider
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer

val COMPONENT_TYPE_ADAPTER = object : TypeAdapter<Component>() {
    override fun write(out: JsonWriter, value: Component?) {
        out.jsonValue(
            GsonComponentSerializer.gson().serialize(value!!)
        )
    }

    override fun read(`in`: JsonReader): Component {
        return GsonComponentSerializer.gson().deserialize(JsonParser.parseReader(`in`).toString())
    }
}

val INT_PROVIDER_TYPE_ADAPTER = object : TypeAdapter<IntProvider>() {
    override fun write(out: JsonWriter, value: IntProvider) {
        out.jsonValue(IntProvider.TRANSCODER.from(value).toString())
    }

    override fun read(reader: JsonReader): IntProvider {
        return when (reader.peek()) {
            JsonToken.NUMBER -> {
                IntProvider.Constant(reader.nextInt())
            }

            JsonToken.BEGIN_OBJECT -> {
                return IntProvider.TRANSCODER.to(JsonParser.parseReader(reader))
            }

            else -> {
                reader.skipValue()
                throw JsonParseException("expected number or object")
            }
        }
    }

}

val STYLE_TYPE_ADAPTER = object : TypeAdapter<Style>() {
    private val gson = GsonComponentSerializer.gson().serializer()

    override fun write(out: JsonWriter, style: Style?) {
        if (style == null) {
            out.nullValue()
            return
        }

        out.jsonValue(gson.toJson(style))
    }

    override fun read(`in`: JsonReader): Style? {
        return gson.fromJson(JsonParser.parseReader(`in`), Style::class.java)
    }
}

val KEY_TYPE_ADAPTER = object : TypeAdapter<Key>() {
    override fun write(out: JsonWriter, value: Key) {
        out.jsonValue("\"${value.asString()}\"")
    }

    override fun read(`in`: JsonReader): Key {
        return Key.key(JsonParser.parseReader(`in`).asString)
    }
}