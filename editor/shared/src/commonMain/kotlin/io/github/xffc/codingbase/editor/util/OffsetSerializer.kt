package io.github.xffc.codingbase.editor.util

import androidx.compose.ui.geometry.Offset
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.FloatArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object OffsetSerializer : KSerializer<Offset> {
    typealias SerializableOffset = @Serializable(with = OffsetSerializer::class) Offset

    private val offsetSerializer = FloatArraySerializer()

    override val descriptor = SerialDescriptor(Offset::class.qualifiedName!!, offsetSerializer.descriptor)

    override fun serialize(encoder: Encoder, value: Offset) {
        encoder.encodeSerializableValue(
            offsetSerializer,
            floatArrayOf(value.x, value.y)
        )
    }

    override fun deserialize(decoder: Decoder): Offset {
        val data = decoder.decodeSerializableValue(offsetSerializer)
        return Offset(data[0], data[1])
    }
}