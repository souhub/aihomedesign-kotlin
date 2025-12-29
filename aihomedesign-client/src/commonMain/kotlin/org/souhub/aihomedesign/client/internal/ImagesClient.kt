package org.souhub.aihomedesign.client.internal

import org.souhub.aihomedesign.client.Images
import org.souhub.aihomedesign.client.internal.http.AIHomeDesignRequester
import org.souhub.aihomedesign.client.internal.http.perform
import org.souhub.aihomedesign.client.models.ImageUpload
import org.souhub.aihomedesign.client.models.ImageUploadRequest
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import org.souhub.aihomedesign.client.models.ItemRemovalMaskUpload
import org.souhub.aihomedesign.client.models.ItemRemovalUpload
import org.souhub.aihomedesign.client.internal.models.ServiceType

internal class ImagesClient(
    private val requester: AIHomeDesignRequester
) : Images {
    private fun defaultFilename(contentType: ContentType): String = when (contentType) {
        ContentType.parse("image/webp") -> "upload.webp"
        ContentType.Image.JPEG -> "upload.jpg"
        ContentType.parse("image/tiff") -> "upload.tiff"
        ContentType.parse("image/x-fuji-raf") -> "upload.raf"
        ContentType.Image.PNG -> "upload.png"
        else -> "upload.bin"
    }

    private suspend fun uploadImage(request: ImageUploadRequest, serviceName: ServiceType): ImageUpload {
        return requester.perform {
            it.submitFormWithBinaryData(
                url = "order/image",
                formData = formData {

                    append("image", request.image, Headers.build {
                        append(HttpHeaders.ContentType, request.contentType.toString())
                        append(HttpHeaders.ContentDisposition, "filename=\"${defaultFilename(request.contentType)}\"")
                    })

                    append("service_name", serviceName.value)
                }
            )
        }
    }

    override suspend fun uploadItemRemovalImage(request: ImageUploadRequest): ItemRemovalUpload {
        return uploadImage(request, ServiceType.ItemRemoval) as ItemRemovalUpload
    }

    override suspend fun uploadItemRemovalMaskImage(request: ImageUploadRequest): ItemRemovalMaskUpload {
        return uploadImage(request, ServiceType.ItemRemovalMask) as ItemRemovalMaskUpload
    }
}