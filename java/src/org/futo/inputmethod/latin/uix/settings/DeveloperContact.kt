package org.futo.inputmethod.latin.uix.settings

/*
 * Single source of truth for MyKey's developer contact details.
 *
 * Previously these were string literals buried inside the About card, which
 * meant the same handle had to be edited in several places (app, README, site)
 * and could silently drift. Now the app reads from one object; the website
 * mirrors the same values in its own config.
 */
object DeveloperContact {
    const val NAME = "Eyasin Arafat"
    const val ROLE = "Developer & Designer"

    const val INSTAGRAM_HANDLE = "@explorearafat"
    const val TIKTOK_HANDLE = "@explorearafat"
    const val FACEBOOK_HANDLE = "/exploreeyasin"
    const val WHATSAPP_DISPLAY = "+880 1610 709657"

    const val INSTAGRAM = "https://instagram.com/explorearafat"
    const val TIKTOK = "https://tiktok.com/@explorearafat"
    const val FACEBOOK = "https://facebook.com/exploreeyasin"
    const val WHATSAPP = "https://wa.me/8801610709657"
}