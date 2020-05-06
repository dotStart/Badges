/*
 * Copyright 2020 Johannes Donath <johannesd@torchmind.com>
 * and other copyright owners as documented in the project's IP log.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tv.dotstart.badge.support.http

import com.github.nwillc.ksvg.elements.SVG
import org.reactivestreams.Publisher
import org.springframework.core.ResolvableType
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.http.MediaType
import org.springframework.http.codec.HttpMessageEncoder
import org.springframework.util.MimeType
import reactor.core.publisher.Flux
import java.nio.charset.StandardCharsets

/**
 * Converts SVG objects into their actual XML representation for in-browser rendering.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 06/05/2020
 */
class SVGHttpMessageEncoder : HttpMessageEncoder<SVG> {

  companion object {
    private val svgMediaType = MediaType("image", "svg+xml", StandardCharsets.UTF_8)
  }

  override fun getEncodableMimeTypes() = listOf(svgMediaType)
  override fun getStreamingMediaTypes() = emptyList<MediaType>()

  override fun canEncode(elementType: ResolvableType, mimeType: MimeType?): Boolean {
    return elementType.isAssignableFrom(SVG::class.java) &&
        (mimeType == null || mimeType == svgMediaType)
  }

  override fun encode(inputStream: Publisher<out SVG>,
                      bufferFactory: DataBufferFactory,
                      elementType: ResolvableType, mimeType:
                      MimeType?,
                      hints: MutableMap<String, Any>?) =
      Flux.from(inputStream).map {
        val bytes = it.toString()
            .toByteArray(StandardCharsets.UTF_8)

        val dataBuffer = bufferFactory.allocateBuffer(bytes.size)
        dataBuffer.write(bytes)
        dataBuffer
      }
}
