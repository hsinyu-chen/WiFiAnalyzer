/*
 * WiFiAnalyzer
 * Copyright (C) 2015 - 2023 VREM Software Development <VREMSoftwareDevelopment@gmail.com>
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
package com.vrem.wifianalyzer.wifi.accesspoint

import android.view.View
import com.vrem.util.buildMinVersionP
import com.vrem.wifianalyzer.MainActivity
import com.vrem.wifianalyzer.MainContext
import com.vrem.wifianalyzer.R
import com.vrem.wifianalyzer.wifi.model.WiFiData
import com.vrem.wifianalyzer.wifi.model.WiFiDetail

class WarningView(private val mainActivity: MainActivity) {

    fun update(wiFiData: WiFiData): Boolean {
        val registered = mainActivity.currentNavigationMenu().registered()
        val noData = noData(registered, wiFiData.wiFiDetails)
        val noLocation = noLocation(registered)
        val warning = noData || noLocation
        mainActivity.findViewById<View>(R.id.warning).visibility = if (warning) View.VISIBLE else View.GONE
        return warning
    }

    internal fun noData(registered: Boolean, wiFiDetails: List<WiFiDetail>): Boolean {
        val noData = registered && wiFiDetails.isEmpty()
        mainActivity.findViewById<View>(R.id.no_data).visibility = if (noData) View.VISIBLE else View.GONE
        return noData
    }

    internal fun noLocation(registered: Boolean): Boolean {
        val noLocation = registered && !MainContext.INSTANCE.permissionService.enabled()
        if (noLocation) {
            mainActivity.findViewById<View>(R.id.no_location).visibility = View.VISIBLE
            mainActivity.findViewById<View>(R.id.throttling).visibility =
                if (buildMinVersionP()) View.VISIBLE else View.GONE
        } else {
            mainActivity.findViewById<View>(R.id.no_location).visibility = View.GONE
            mainActivity.findViewById<View>(R.id.throttling).visibility = View.GONE
        }
        return noLocation
    }

}