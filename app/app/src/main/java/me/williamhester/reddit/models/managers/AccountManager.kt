package me.williamhester.reddit.models.managers

import me.williamhester.reddit.models.Account

/** A singleton containing the current logged-in account */
class AccountManager {

  var account: Account? = null

  val isLoggedIn: Boolean
    get() = account != null
}
