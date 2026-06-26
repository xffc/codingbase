package io.github.xffc.codingbase.creative.util

import io.github.xffc.codingbase.creative.CreativePlugin
import io.github.xffc.codingbase.creative.code.CodeMethod
import io.github.xffc.codingbase.creative.code.events.CreativeEvent
import io.github.xffc.codingbase.creative.code.options.CodeMethodOption
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.putJsonObject
import java.io.File

object DataExporter {
    fun export() {
        write("events.json", CreativeEvent.registry.keys)

        write("methods.json", CodeMethod.registry.mapValues {
            buildJsonObject {
                put("type", JsonPrimitive(
                    when (it.value) {
                        is CodeMethod.Action -> "action"
                        is CodeMethod.Condition -> "condition"
                    }
                ))

                put("options", buildJsonArray {
                    it.value.options.forEach { option ->
                        add(JsonPrimitive(option.name))
                    }
                })
            }
        })
    }

    private inline fun <reified T> write(fileName: String, values: T) {
        File(CreativePlugin.dataFolder, fileName).bufferedWriter().use {
            it.write(Json.encodeToString(values))
        }
    }
}