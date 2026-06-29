package io.github.xffc.codingbase.webeditor.util

import androidx.compose.ui.geometry.Offset
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

typealias SerializableOffset = @Serializable(with = OffsetSerializer::class) Offset

@Serializable
data class OffsetData(val x: Float, val y: Float)

object OffsetSerializer: KSerializer<Offset> {
    override val descriptor: SerialDescriptor = OffsetData.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Offset) {
        encoder.encodeFloat(value.x)
        encoder.encodeFloat(value.y)
    }

    override fun deserialize(decoder: Decoder): Offset {
        val x = decoder.decodeFloat()
        val y = decoder.decodeFloat()
        return Offset(x, y)
    }
}