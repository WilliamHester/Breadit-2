package williamhester.me.breadit2.models.managers;

import williamhester.me.breadit2.models.Account;

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
