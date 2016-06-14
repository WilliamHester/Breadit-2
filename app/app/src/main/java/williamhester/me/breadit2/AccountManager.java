package williamhester.me.breadit2;

/**
 * Created by william on 6/13/16.
 */
public class AccountManager {

  private Account mAccount;

  public Account getAccount() {
    return mAccount;
  }

  public void setAccount(Account account) {
    mAccount = account;
  }

  public boolean isLoggedIn() {
    return mAccount != null;
  }
}
