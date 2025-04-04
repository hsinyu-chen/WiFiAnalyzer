/*
 * WiFiAnalyzer
 * Copyright (C) 2015 - 2025 VREM Software Development <VREMSoftwareDevelopment@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.vrem.wifianalyzer.wifi.model

import android.net.wifi.ScanResult
import kotlin.math.abs

typealias ChannelWidth = Int

typealias CalculateCenter = (primary: Int, center0: Int, center1: Int) -> Int

internal val calculateCenter20: CalculateCenter = { primary, _, _ -> primary }

internal val calculateCenter40: CalculateCenter = { primary, center0, _ ->
    if (abs(primary - center0) >= WiFiWidth.MHZ_40.frequencyWidthHalf) {
        (primary + center0) / 2
    } else {
        center0
    }
}

internal val calculateCenter80: CalculateCenter = { _, center0, _ -> center0 }

internal val calculateCenter160: CalculateCenter = { _, _, center1 -> center1 }

internal val calculateCenter320: CalculateCenter = { _, _, center1 -> center1 }

enum class WiFiWidth(
    val channelWidth: ChannelWidth,
    val frequencyWidth: Int,
    val guardBand: Int,
    val calculateCenter: CalculateCenter
) {
    MHZ_20(ScanResult.CHANNEL_WIDTH_20MHZ, 20, 2, calculateCenter20),
    MHZ_40(ScanResult.CHANNEL_WIDTH_40MHZ, 40, 3, calculateCenter40),
    MHZ_80(ScanResult.CHANNEL_WIDTH_80MHZ, 80, 3, calculateCenter80),
    MHZ_160(ScanResult.CHANNEL_WIDTH_160MHZ, 160, 3, calculateCenter160),
    MHZ_80_PLUS(ScanResult.CHANNEL_WIDTH_80MHZ_PLUS_MHZ, 80, 3, calculateCenter80),
    MHZ_320(ScanResult.CHANNEL_WIDTH_320MHZ, 320, 3, calculateCenter320);

    val frequencyWidthHalf: Int = frequencyWidth / 2

    companion object {
        fun findOne(channelWidth: ChannelWidth): WiFiWidth =
            WiFiWidth.entries.firstOrNull { it.channelWidth == channelWidth } ?: MHZ_20
    }
}
