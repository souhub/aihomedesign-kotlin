package org.souhub.aihomedesign.client.internal

/**
 * Object to handle API paths.
 */
internal object ApiPath {
    private const val VERSION = "v1"
    private fun build(path: String) = "$VERSION/${path.removePrefix("/")}"

    val SPACES = build("spaces")
    val IMAGE_UPLOAD = build("order/image")
    val ORDER = build("order")
    val MANUAL_MASK = build("order/manual-mask")
    val FINALIZE_ORDER = build("order/done")
}