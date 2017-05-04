package me.williamhester.reddit.models.managers;

import me.williamhester.reddit.models.Account;

/**
 * Created by william on 6/13/16.
 */
public class AccountManager {

  private Account account;

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public boolean isLoggedIn() {
    return account != null;
  }
}
