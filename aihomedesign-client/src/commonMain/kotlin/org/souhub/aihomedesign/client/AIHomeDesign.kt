package org.souhub.aihomedesign.client

import org.souhub.aihomedesign.client.internal.DefaultAIHomeDesign
import org.souhub.aihomedesign.client.models.FinalizedOrder
import org.souhub.aihomedesign.client.models.MaskedImage
import org.souhub.aihomedesign.client.internal.http.HttpTransport
import org.souhub.aihomedesign.client.internal.createHttpClient
import org.souhub.aihomedesign.client.models.ImageId
import org.souhub.aihomedesign.client.models.ImageUploadRequest
import org.souhub.aihomedesign.client.models.ItemRemovalMaskUpload
import org.souhub.aihomedesign.client.models.ItemRemovalUpload
import org.souhub.aihomedesign.client.models.Order
import org.souhub.aihomedesign.client.models.Space

public interface AIHomeDesign {
    public val spaces: Spaces
    public val images: Images
    public val orders: Orders

    public companion object {
        public operator fun invoke(apiKey: String): AIHomeDesign {
            return invoke(AIHomeDesignConfig(apiKey = apiKey))
        }

        public operator fun invoke(config: AIHomeDesignConfig): AIHomeDesign {
            val httpClient = createHttpClient(config)
            val transport = HttpTransport(httpClient)
            return DefaultAIHomeDesign(transport)
        }
    }
}

public interface Spaces {
    public suspend fun getSpaces(active: Boolean? = null, serviceName: String? = null): List<Space>
}

public interface Images {
    public suspend fun uploadItemRemovalImage(request: ImageUploadRequest): ItemRemovalUpload

    public suspend fun uploadItemRemovalMaskImage(request: ImageUploadRequest): ItemRemovalMaskUpload
}

public interface Orders {
    public suspend fun submitItemRemovalOrder(upload: ItemRemovalUpload): Order

    public suspend fun submitItemRemovalMaskOrder(upload: ItemRemovalMaskUpload): Order

    public suspend fun applyManualMask(upload: ItemRemovalMaskUpload, maskImage: ByteArray): Order

    public suspend fun retrieveLatestMaskedImage(upload: ItemRemovalMaskUpload): MaskedImage

    public suspend fun finalizeOrder(upload: ItemRemovalMaskUpload, imageId: ImageId): FinalizedOrder
}