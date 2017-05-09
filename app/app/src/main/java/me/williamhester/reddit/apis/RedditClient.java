package me.williamhester.reddit.apis;

import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;

import me.williamhester.reddit.convert.RedditGsonConverter;
import me.williamhester.reddit.messages.PostMessage;
import me.williamhester.reddit.messages.VotableListMessage;
import me.williamhester.reddit.models.Submission;

/** Contains methods to communicate with Reddit */
public class RedditClient {

  private final RedditGsonConverter redditGsonConverter;
  private final RedditHttpRequestFactory requestFactory;

  public RedditClient(RedditGsonConverter redditGsonConverter, RedditHttpRequestFactory requestFactory) {
    this.redditGsonConverter = redditGsonConverter;
    this.requestFactory = requestFactory;
  }

  public void getSubmissions(String place, String query, String after) {
    Map<String, String> queries = new HashMap<>();
    if (after != null) {
      queries.put("after", after);
    }
    requestFactory.createRequest(place, queries, new RedditHttpRequestFactory.Converter() {
      @Override
      public Object convert(JsonElement element) throws Exception {
        return new VotableListMessage(redditGsonConverter.<Submission>toList(element));
      }
    });
  }

  public void getComments(String permalink) {
    requestFactory.createRequest(permalink, new RedditHttpRequestFactory.Converter() {
      @Override
      public Object convert(JsonElement element) throws Exception {
        return new PostMessage(redditGsonConverter.toPost(element));
      }
    });
  }
}
