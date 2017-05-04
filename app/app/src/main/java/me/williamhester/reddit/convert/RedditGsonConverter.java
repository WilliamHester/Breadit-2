package me.williamhester.reddit.convert;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.LinkedList;
import java.util.List;

import me.williamhester.reddit.models.Account;
import me.williamhester.reddit.models.Comment;
import me.williamhester.reddit.models.Edited;
import me.williamhester.reddit.models.MoreComment;
import me.williamhester.reddit.models.MoreCommentJson;
import me.williamhester.reddit.models.Post;
import me.williamhester.reddit.models.Submission;
import me.williamhester.reddit.models.SubmissionJson;
import me.williamhester.reddit.models.TextComment;
import me.williamhester.reddit.models.TextCommentJson;
import me.williamhester.reddit.models.Votable;
import me.williamhester.reddit.models.VoteStatus;

/**
 * A class for converting Gson objects to Reddit objects
 */
@SuppressWarnings("unchecked") // Assume the caller knows what they're asking for
public class RedditGsonConverter {

  private final Gson gson;

  public RedditGsonConverter(Gson gson) {
    this.gson = gson;
  }

  public <T extends Votable> List<T> toList(JsonElement element) throws ConverterException {
    return toList(element.getAsJsonObject(), 0);
  }

  private <T extends Votable> List<T> toList(JsonObject object, int level) throws ConverterException {
    if (!object.get("kind").getAsString().equals("Listing")) {
      throw new ConverterException(object, "Listing");
    }
    JsonArray children = object
        .get("data")
        .getAsJsonObject()
        .get("children")
        .getAsJsonArray();

    List things = new LinkedList<>();
    for (JsonElement e : children) {
      JsonObject obj = e.getAsJsonObject();
      String type = getKind(obj);
      switch (type) {
        case "t1":
          toTextComment(things, obj, level);
          break;
        case "t2":
          things.add(toAccount(obj));
          break;
        case "t3":
          things.add(toSubmission(obj));
          break;
        case "t4":
//          things.add(toMessage(obj));
          break;
        case "t5":
//          things.add(toSubreddit(obj));
          break;
        case "more":
          things.add(toMoreComment(obj, level));
          break;
        default:
          throw new ConverterException(obj, "");
      }
    }
    return (List<T>) things;
  }

  public Post toPost(JsonElement element) throws ConverterException {
    if (!element.isJsonArray()) {
      throw new ConverterException(element, "Array containing a submission and a list of comments");
    }

    JsonArray array = element.getAsJsonArray();
    Submission submission = (Submission) toList(array.get(0)).get(0);
    List<Comment> comments = toList(array.get(1));

    return new Post(submission, comments);
  }

  /**
   * Converts the {@link JsonObject} to a {@link Account}
   */
  public Account toAccount(JsonObject object) {
    return null;
  }

  /**
   * Converts the {@link JsonObject} to a {@link Edited}
   */
  public Edited toEdited(JsonObject object) {
    return null;
  }

  /**
   * Converts the {@link JsonObject} to a {@link MoreComment}
   */
  public MoreComment toMoreComment(JsonObject object, int level) {
    return new MoreComment(
        gson.fromJson(object.get("data"), MoreCommentJson.class), level);
  }

  /**
   * Converts the {@link JsonObject} to a {@link Submission}
   */
  public Submission toSubmission(JsonObject object) {
    return new Submission(gson.fromJson(getData(object), SubmissionJson.class));
  }

  /**
   * Converts the {@link JsonObject} to a {@link TextComment}
   */
  public TextComment toTextComment(List comments, JsonObject object) throws ConverterException {
    return toTextComment(comments, object, 0);
  }

  public TextComment toTextComment(List comments, JsonObject object, int level)
      throws ConverterException {
    JsonObject data = getData(object);
    TextComment comment =
        new TextComment(gson.fromJson(data, TextCommentJson.class), level);

    comments.add(comment);

    JsonElement replies = data.get("replies");
    if (replies != null && replies.isJsonObject()) {
      comments.addAll(toList(replies.getAsJsonObject(), level + 1));
    }

    return comment;
  }

  /**
   * Converts the {@link JsonObject} to a {@link Votable}
   */
  public Votable toVotable(JsonObject object) {
    return null;
  }

  /**
   * Converts the {@link JsonObject} to a {@link VoteStatus}
   */
  public VoteStatus toVoteStatus(JsonObject object) {
    return null;
  }

  private static String getKind(JsonObject o) {
    return o.get("kind").getAsString();
  }

  private static JsonObject getData(JsonObject o) {
    return o.get("data").getAsJsonObject();
  }
}
