package org.futo.inputmethod.announcements

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.futo.inputmethod.latin.BuildConfig
import org.json.JSONArray
import org.json.JSONObject

@Serializable
data class Announcement(
    val id: String,
    val title: String,
    val date: String = "",
    val badge: String = "NEW",
    val summary: String = "",
    val highlights: List<String> = emptyList(),
    val actionLabel: String? = null,
    val actionUrl: String? = null,
    val secondaryActionLabel: String? = null,
    val minVersionCode: Int = 0,
    val isCritical: Boolean = false,
    val message: String = summary,
    val actionText: String? = actionLabel,
    val maxVersionCode: Int = Int.MAX_VALUE,
    val autoShow: Boolean = false
) {
    val displaySummary: String
        get() = summary.ifBlank { message }

    val displayActionLabel: String?
        get() = actionLabel ?: actionText
}

object AnnouncementParser {
    val jsonFormat = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
        encodeDefaults = true
    }

    fun parse(jsonString: String, currentCode: Int = BuildConfig.VERSION_CODE): Announcement? {
        val trimmed = jsonString.trim()
        if (trimmed.isBlank() || (!trimmed.startsWith("[") && !trimmed.startsWith("{"))) return null

        return runCatching {
            val list = if (trimmed.startsWith("[")) {
                jsonFormat.decodeFromString<List<Announcement>>(trimmed)
            } else {
                listOf(jsonFormat.decodeFromString<Announcement>(trimmed))
            }
            list.firstOrNull { currentCode in it.minVersionCode..it.maxVersionCode }?.let { sanitize(it) }
        }.getOrElse {
            parseFallback(trimmed, currentCode)
        }
    }

    private fun sanitize(item: Announcement): Announcement {
        val sum = item.summary.ifBlank { item.message }
        val msg = item.message.ifBlank { item.summary }
        val actL = item.actionLabel ?: item.actionText
        val actT = item.actionText ?: item.actionLabel
        return item.copy(
            summary = sum,
            message = msg,
            actionLabel = actL,
            actionText = actT
        )
    }

    private fun parseFallback(trimmed: String, currentCode: Int): Announcement? {
        return runCatching {
            if (trimmed.startsWith("[")) {
                val array = JSONArray(trimmed)
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    val ann = parseItemFallback(obj)
                    if (currentCode in ann.minVersionCode..ann.maxVersionCode) return ann
                }
                null
            } else if (trimmed.startsWith("{")) {
                val obj = JSONObject(trimmed)
                val ann = parseItemFallback(obj)
                if (currentCode in ann.minVersionCode..ann.maxVersionCode) ann else null
            } else {
                null
            }
        }.getOrNull()
    }

    private fun parseItemFallback(obj: JSONObject): Announcement {
        val highlightsList = mutableListOf<String>()
        val highlightsArray = obj.optJSONArray("highlights")
        if (highlightsArray != null) {
            for (i in 0 until highlightsArray.length()) {
                val item = highlightsArray.optString(i, "")
                if (item.isNotBlank()) highlightsList.add(item)
            }
        }

        val summary = obj.optString("summary", "").ifBlank { obj.optString("message", "") }
        val actionLabel = obj.optString("actionLabel", "").ifBlank { obj.optString("actionText", "") }

        return Announcement(
            id = obj.optString("id", "announcement_${System.currentTimeMillis()}"),
            title = obj.optString("title", "Announcement"),
            date = obj.optString("date", ""),
            badge = obj.optString("badge", "NEW"),
            summary = summary,
            highlights = highlightsList,
            actionLabel = actionLabel.ifBlank { null },
            actionUrl = obj.optString("actionUrl", "").ifBlank { null },
            secondaryActionLabel = obj.optString("secondaryActionLabel", "").ifBlank { null },
            minVersionCode = obj.optInt("minVersionCode", 0),
            maxVersionCode = obj.optInt("maxVersionCode", Int.MAX_VALUE),
            isCritical = obj.optBoolean("isCritical", false),
            autoShow = obj.optBoolean("autoShow", false)
        )
    }
}
