package org.souhub.aihomedesign.client

import org.souhub.aihomedesign.client.internal.DefaultAIHomeDesign
import org.souhub.aihomedesign.client.models.MaskedImage
import org.souhub.aihomedesign.client.internal.http.HttpTransport
import org.souhub.aihomedesign.client.internal.createHttpClient
import org.souhub.aihomedesign.client.models.ApplyManualMaskResponse
import org.souhub.aihomedesign.client.models.ServiceName
import org.souhub.aihomedesign.client.models.FinalizeOrderResponse
import org.souhub.aihomedesign.client.models.GetSpacesResponse
import org.souhub.aihomedesign.client.models.ImageId
import org.souhub.aihomedesign.client.models.ItemRemovalMaskOrderId
import org.souhub.aihomedesign.client.models.ItemRemovalOrderId
import org.souhub.aihomedesign.client.models.SubmitOrderResponse

/**
 * AI Home Design API client.
 *
 * Provides access to AI Home Design services for image processing and space catalog browsing.
 */
public interface AIHomeDesign {
    /**
     * Access to spaces catalog.
     */
    public val catalog: Catalog

    /**
     * AI item removal service.
     */
    public val itemRemoval: ItemRemoval

    /**
     * AI item removal service with manual mask editing capabilities.
     */
    public val itemRemovalMask: ItemRemovalMask

    public companion object {
        /**
         * Creates an AI Home Design client instance.
         *
         * @param apiKey API key for authentication.
         */
        public operator fun invoke(apiKey: String): AIHomeDesign {
            return invoke(AIHomeDesignConfig(apiKey = apiKey))
        }

        /**
         * Creates an AI Home Design client instance.
         *
         * @param config client configuration.
         */
        public operator fun invoke(config: AIHomeDesignConfig): AIHomeDesign {
            val httpClient = createHttpClient(config)
            val transport = HttpTransport(httpClient)
            return DefaultAIHomeDesign(transport)
        }
    }
}

/**
 * AI item removal service.
 */
public interface ItemRemoval {
    /**
     * Uploads an image for item removal processing.
     *
     * @param imageBytes image data as a byte array.
     * @param mineType optional MIME type (e.g., "image/png", "image/x-sony-arw"). If null, automatically detected.
     */
    public suspend fun uploadImage(imageBytes: ByteArray, mineType: String? = null): ItemRemovalOrderId

    /**
     * Submits an order for processing.
     *
     * @param orderId order ID obtained from [uploadImage].
     */
    public suspend fun submitOrder(orderId: ItemRemovalOrderId): SubmitOrderResponse
}

/**
 * AI item removal service with manual mask editing capabilities.
 */
public interface ItemRemovalMask {
    /**
     * Uploads an image for item removal with mask processing.
     *
     * @param imageBytes image data as a byte array.
     * @param mineType optional MIME type (e.g., "image/png", "image/x-sony-arw"). If null, automatically detected.
     */
    public suspend fun uploadImage(imageBytes: ByteArray, mineType: String? = null): ItemRemovalMaskOrderId

    /**
     * Submits an order for processing.
     *
     * @param orderId order ID obtained from [uploadImage].
     */
    public suspend fun submitOrder(orderId: ItemRemovalMaskOrderId): SubmitOrderResponse

    /**
     * Applies a manual mask to the order.
     *
     * @param orderId order ID.
     * @param maskImage mask image data as a byte array.
     */
    public suspend fun applyManualMask(orderId: ItemRemovalMaskOrderId, maskImage: ByteArray): ApplyManualMaskResponse

    /**
     * Retrieves the latest temporary masked image.
     *
     * @param orderId order ID.
     */
    public suspend fun getLastTempImage(orderId: ItemRemovalMaskOrderId): MaskedImage

    /**
     * Finalizes the order with the selected image.
     *
     * @param orderId order ID.
     * @param imageId image ID to finalize.
     */
    public suspend fun finalizeOrder(orderId: ItemRemovalMaskOrderId, imageId: ImageId): FinalizeOrderResponse
}

/**
 * Catalog service for browsing available spaces.
 */
public interface Catalog {
    /**
     * Retrieves available spaces.
     *
     * @param active filter by active status.
     * @param serviceName filter by service name.
     */
    public suspend fun getSpaces(active: Boolean? = null, serviceName: ServiceName? = null): GetSpacesResponse
}