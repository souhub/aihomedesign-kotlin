package org.souhub.aihomedesign.client.internal

import org.souhub.aihomedesign.client.AIHomeDesign
import org.souhub.aihomedesign.client.Images
import org.souhub.aihomedesign.client.Orders
import org.souhub.aihomedesign.client.Spaces
import org.souhub.aihomedesign.client.internal.http.AIHomeDesignRequester

internal class DefaultAIHomeDesign(
    private val requester: AIHomeDesignRequester,
) : AIHomeDesign {
    override val spaces: Spaces = SpacesClient(requester)
    override val images: Images = ImagesClient(requester)
    override val orders: Orders = OrdersClient(requester)
}