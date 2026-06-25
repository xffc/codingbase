package io.github.xffc.codingbase.creative.util

import io.github.xffc.codingbase.creative.extensions.json
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.text.Component

object ComponentSerializer: KSerializer<Component> {
    override val descriptor = PrimitiveSerialDescriptor(Component::class.qualifiedName!!, PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Component) =
        encoder.encodeString(value.json)

    override fun deserialize(decoder: Decoder): Component =
        decoder.decodeString().json
}