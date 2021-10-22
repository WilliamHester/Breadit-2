package me.williamhester.reddit.models.managers

import android.content.SharedPreferences
import android.util.Log
import io.realm.Realm
import me.williamhester.reddit.models.Account

/** A singleton containing the current logged-in account */
class AccountManager(private val sharedPreferences: SharedPreferences) {

  var username: String? = null
    private set

  var accessToken: String? = null
    private set

  var refreshToken: String? = null
    private set

  fun setAccount(account: Account?) {
    val prefs = sharedPreferences.edit()
    prefs.putString("activeUser", account?.username)
    prefs.apply()

    username = account?.username
    accessToken = account?.accessToken
    refreshToken = account?.refreshToken
  }

  val isLoggedIn: Boolean
    get() = accessToken != null

  init {
    val activeUser = sharedPreferences.getString("activeUser", null)
    if (activeUser != null) {
      Log.d("AccountManager", "Logged in as $activeUser")
      setAccount(
          Realm.getDefaultInstance()
              .where(Account::class.java)
              .equalTo("username", activeUser)
              .findFirst())
    } else {
      Log.d("AccountManager", "Not logged in")
    }
  }
}
