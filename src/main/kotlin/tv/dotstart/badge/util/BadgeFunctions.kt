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
package tv.dotstart.badge.util

import com.github.nwillc.ksvg.elements.SVG
import kotlin.math.roundToInt

/**
 * Provides functions for generating arbitrary badges.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 06/05/2020
 */

private const val textPadding = 8
private const val badgeHeight = 20

private const val fontFamily = "Roboto,DejaVu Sans,Verdana,Geneva,Helvetica Neue,Helvetica,sans-serif"
private const val fontSize = 11
private const val fontWidth = 6.5

private const val titleColor = "#555"
private const val titleTextColor = "#fff"
private const val textShadowColor = "#010101"
private const val shadowOpacity = 0.3

private const val shadowOffset = 1
private const val textYOffset = 14

fun badge(title: String, value: String, color: Color): SVG {
  val titleWidth = ((title.length * fontWidth) + (textPadding * 2))
      .roundToInt()
  val valueWidth = ((value.length * fontWidth) + (textPadding * 2))
      .roundToInt()

  return SVG.svg {
    attributes["xmlns"] = "http://www.w3.org/2000/svg"
    attributes["version"] = "1.1"

    width = (titleWidth + valueWidth).toString()
    height = badgeHeight.toString()

    g {
      rect {
        height = badgeHeight.toString()
        width = titleWidth.toString()

        fill = titleColor
      }
      rect {
        height = badgeHeight.toString()
        width = valueWidth.toString()
        x = titleWidth.toString()

        fill = color.hexValue
      }
    }
    g {
      attributes["font-family"] = fontFamily
      attributes["font-size"] = fontSize.toString()

      // title
      text {
        x = (textPadding + shadowOffset).toString()
        y = (textYOffset + shadowOffset).toString()

        fill = textShadowColor
        attributes["fill-opacity"] = shadowOpacity.toString()

        body = title
      }
      text {
        x = textPadding.toString()
        y = textYOffset.toString()
        fill = titleTextColor

        body = title
      }

      // value
      text {
        x = (titleWidth + textPadding).toString()
        y = (textYOffset + shadowOffset).toString()

        fill = textShadowColor
        attributes["fill-opacity"] = shadowOpacity.toString()

        body = value
      }
      text {
        x = (titleWidth + textPadding).toString()
        y = textYOffset.toString()

        fill = color.hexTextValue

        body = value
      }
    }
  }
}
