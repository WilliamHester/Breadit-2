package me.williamhester.reddit.models;

/**
 * The JSON representation of the access token response, as defined at
 * https://github.com/reddit/reddit/wiki/OAuth2#retrieving-the-access-token
 */
public class AccessTokenJson {
  private String access_token;
  private String token_type;
  private int expires_in;
  private String scope;
  private String refresh_token;

  public String getAccessToken() {
    return access_token;
  }

  public String getTokenType() {
    return token_type;
  }

  public int getExpiresIn() {
    return expires_in;
  }

  public String getScope() {
    return scope;
  }

  public String getRefreshToken() {
    return refresh_token;
  }
}
