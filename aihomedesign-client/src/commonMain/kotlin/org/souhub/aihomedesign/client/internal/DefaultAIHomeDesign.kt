package org.souhub.aihomedesign.client.internal

import org.souhub.aihomedesign.client.AIHomeDesign
import org.souhub.aihomedesign.client.Catalog
import org.souhub.aihomedesign.client.ItemRemoval
import org.souhub.aihomedesign.client.ItemRemovalMask
import org.souhub.aihomedesign.client.internal.http.AIHomeDesignRequester

internal class DefaultAIHomeDesign(
    private val requester: AIHomeDesignRequester,
) : AIHomeDesign {
    private val imageUploader: ImageUploader = ImageUploader(requester)

    override val catalog: Catalog = CatalogClient(requester)
    override val itemRemoval: ItemRemoval = ItemRemovalClient(imageUploader, requester)
    override val itemRemovalMask: ItemRemovalMask = ItemRemovalMaskClient(imageUploader, requester)
}