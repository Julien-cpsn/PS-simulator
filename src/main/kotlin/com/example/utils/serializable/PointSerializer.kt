package com.example.utils.serializable

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object PointSerializer : KSerializer<Pair<Double, Double>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Point", PrimitiveKind.DOUBLE)
    override fun serialize(encoder: Encoder, value: Pair<Double, Double>) {
        encoder.encodeDouble(value.first)
        encoder.encodeDouble(value.second)
    }

    override fun deserialize(decoder: Decoder): Pair<Double, Double> {
        return Pair(1.0, 2.5)
    }
}