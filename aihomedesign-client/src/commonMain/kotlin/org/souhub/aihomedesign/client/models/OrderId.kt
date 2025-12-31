package org.souhub.aihomedesign.client.models

import kotlinx.serialization.Serializable
import org.souhub.aihomedesign.client.internal.models.OrderId

@Serializable
@JvmInline
public value class ItemRemovalOrderId private constructor(public override val value: String) : OrderId {
    public companion object {
        public fun from(raw: String): ItemRemovalOrderId {
            val v = raw.trim()
            require(v.isNotEmpty()) { "orderId must not be blank" }
            return ItemRemovalOrderId(v)
        }
    }
}

@Serializable
@JvmInline
public value class ItemRemovalMaskOrderId private constructor(public override val value: String): OrderId {
    public companion object {
        public fun from(raw: String): ItemRemovalMaskOrderId {
            val v = raw.trim()
            require(v.isNotEmpty()) { "orderId must not be blank" }
            return ItemRemovalMaskOrderId(v)
        }
    }
}