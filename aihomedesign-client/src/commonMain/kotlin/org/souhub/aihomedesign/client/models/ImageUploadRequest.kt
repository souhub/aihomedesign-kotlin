package org.souhub.aihomedesign.client.models

import io.ktor.http.ContentType

public class ImageUploadRequest(
    public val image: ByteArray,
    public val contentType: ContentType = detectMimeType(image),
) {
    public companion object {
        private fun detectMimeType(b: ByteArray): ContentType {
            if (b.size >= 8 &&
                b[0] == 0x89.toByte() && b[1] == 0x50.toByte() && b[2] == 0x4E.toByte() && b[3] == 0x47.toByte() &&
                b[4] == 0x0D.toByte() && b[5] == 0x0A.toByte() && b[6] == 0x1A.toByte() && b[7] == 0x0A.toByte()
            ) return ContentType.Image.PNG

            if (b.size >= 3 && b[0] == 0xFF.toByte() && b[1] == 0xD8.toByte() && b[2] == 0xFF.toByte())
                return ContentType.Image.JPEG

            if (b.size >= 12 &&
                b[0] == 'R'.code.toByte() && b[1] == 'I'.code.toByte() && b[2] == 'F'.code.toByte() && b[3] == 'F'.code.toByte() &&
                b[8] == 'W'.code.toByte() && b[9] == 'E'.code.toByte() && b[10] == 'B'.code.toByte() && b[11] == 'P'.code.toByte()
            ) return ContentType.parse("image/webp")

            if (b.size >= 4 &&
                ((b[0] == 0x49.toByte() && b[1] == 0x49.toByte() && b[2] == 0x2A.toByte() && b[3] == 0x00.toByte()) ||
                        (b[0] == 0x4D.toByte() && b[1] == 0x4D.toByte() && b[2] == 0x00.toByte() && b[3] == 0x2A.toByte()))
            ) return ContentType.parse("image/tiff")

            if (b.size >= 3 && b[0] == 'F'.code.toByte() && b[1] == 'U'.code.toByte() && b[2] == 'J'.code.toByte())
                return ContentType.parse("image/x-fuji-raf")

            return ContentType.parse("application/octet-stream")
        }
    }
}
