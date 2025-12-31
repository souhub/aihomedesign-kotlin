package org.souhub.aihomedesign.client.internal

import org.souhub.aihomedesign.client.internal.http.AIHomeDesignRequester
import org.souhub.aihomedesign.client.internal.http.perform
import org.souhub.aihomedesign.client.internal.models.ImageUploadResponse
import org.souhub.aihomedesign.client.internal.models.ImageUploadRequest
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import org.souhub.aihomedesign.client.models.ServiceName

internal class ImageUploader(
    private val requester: AIHomeDesignRequester
) {
    private fun defaultFilename(contentType: ContentType): String = when (contentType) {
        ContentType.parse("image/webp") -> "upload.webp"
        ContentType.Image.JPEG -> "upload.jpg"
        ContentType.parse("image/tiff") -> "upload.tiff"
        ContentType.parse("image/x-fuji-raf") -> "upload.raf"
        ContentType.Image.PNG -> "upload.png"
        else -> "upload.bin"
    }

    internal suspend fun uploadImage(request: ImageUploadRequest, serviceName: ServiceName): ImageUploadResponse {
        return requester.perform {
            it.submitFormWithBinaryData(
                url = ApiPath.IMAGE_UPLOAD,
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
}