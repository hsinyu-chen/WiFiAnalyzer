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
package com.vrem.wifianalyzer.wifi.band

class WiFiChannelsGHZ6 : WiFiChannels(RANGE, SETS) {
    override fun wiFiChannelPairs(): List<WiFiChannelPair> = SETS

    override fun availableChannels(countryCode: String): List<WiFiChannel> =
        availableChannels(WiFiChannelCountry.find(countryCode).channelsGHZ6())

    override fun channelAvailable(countryCode: String, channel: Int): Boolean =
        WiFiChannelCountry.find(countryCode).channelAvailableGHZ6(channel)

    override fun wiFiChannelByFrequency(frequency: Int, wiFiChannelPair: WiFiChannelPair): WiFiChannel =
        if (inRange(frequency)) wiFiChannel(frequency, wiFiChannelPair) else WiFiChannel.UNKNOWN

    companion object {
        private val SET = WiFiChannelPair(WiFiChannel(1, 5955), WiFiChannel(229, 7095))
        private val SETS = listOf(SET)
        private val RANGE = WiFiRange(5925, 7125)
    }
}
