package williamhester.me.breadit2;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by william on 6/13/16.
 */
public class Account extends RealmObject {

  @PrimaryKey
  private String username;
  private String accessToken;
  private String refreshToken;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
