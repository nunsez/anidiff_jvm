package com.example.anidiff_jvm.serializers

import com.example.anidiff_jvm.entities.EntityStatus
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object MalStatusSerializer : KSerializer<EntityStatus> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("status", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): EntityStatus {
        return when (val status = decoder.decodeInt()) {
            1 -> EntityStatus.Watching
            2 -> EntityStatus.Completed
            3 -> EntityStatus.OnHold
            4 -> EntityStatus.Dropped
            6 -> EntityStatus.Planned
            else -> throw SerializationException("Wrong status: $status")
        }
    }

    override fun serialize(encoder: Encoder, value: EntityStatus) {
        val status = when (value) {
            EntityStatus.Watching -> 1
            EntityStatus.Completed -> 2
            EntityStatus.OnHold -> 3
            EntityStatus.Dropped -> 4
            EntityStatus.Planned -> 6
        }
        encoder.encodeInt(status)
    }
}
