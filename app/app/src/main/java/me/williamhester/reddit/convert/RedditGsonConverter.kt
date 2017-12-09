package me.williamhester.reddit.convert

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import me.williamhester.reddit.models.*
import java.util.*

/** A class for converting Gson objects to Reddit objects */
class RedditGsonConverter(private val gson: Gson) {

  @Throws(ConverterException::class)
  fun <T> toList(element: JsonElement): List<T> = toList(element.asJsonObject, 0)

  @Throws(ConverterException::class)
  private fun <T> toList(jsonObject: JsonObject, level: Int = 0): List<T> {
    if (jsonObject.get("kind").asString != "Listing") {
      throw ConverterException(jsonObject, "Listing")
    }
    val children = jsonObject
        .get("data")
        .asJsonObject
        .get("children")
        .asJsonArray

    val things = LinkedList<Any?>()
    for (e in children) {
      val obj = e.asJsonObject
      val type = getKind(obj)
      when (type) {
        "t1" -> toTextComment(things, obj, level)
        "t2" -> things.add(toAccount(obj))
        "t3" -> things.add(toSubmission(obj))
        "t4" -> {} // things.add(toMessage(jsonObject))
        "t5" -> things.add(toSubreddit(obj))
        "more" -> things.add(toMoreComment(obj, level))
        else -> throw ConverterException(obj, "")
      }
    }
    @Suppress("UNCHECKED_CAST") // Yeah, it's dangerous. Sue me.
    return things as List<T>
  }

  @Throws(ConverterException::class)
  fun toPost(element: JsonElement): Post {
    if (!element.isJsonArray) {
      throw ConverterException(element, "Array containing a submission and a list of comments")
    }

    val array = element.asJsonArray
    val submission = toList<Votable>(array.get(0))[0] as Submission
    val comments = toList<Comment>(array.get(1))

    return Post(submission, comments)
  }

  /**
   * Converts the [JsonObject] to a [Account]
   */
  fun toAccount(`object`: JsonObject): Account? {
    return null
  }

  /**
   * Converts the [JsonObject] to a [Edited]
   */
  fun toEdited(`object`: JsonObject): Edited? {
    return null
  }

  /**
   * Converts the [JsonObject] to a [MoreComment]
   */
  fun toMoreComment(`object`: JsonObject, level: Int): MoreComment {
    return MoreComment(
        gson.fromJson(`object`.get("data"), MoreCommentJson::class.java), level)
  }

  /**
   * Converts the [JsonObject] to a [Submission]
   */
  fun toSubmission(`object`: JsonObject): Submission {
    return Submission(gson.fromJson(getData(`object`), SubmissionJson::class.java))
  }

  /**
   * Converts the [JsonObject] to a [TextComment]
   */
  @Throws(ConverterException::class)
  @JvmOverloads
  fun toTextComment(comments: MutableList<Any?>, `object`: JsonObject, level: Int = 0): TextComment {
    val data = getData(`object`)
    val comment = TextComment(gson.fromJson(data, TextCommentJson::class.java), level)

    comments.add(comment)

    val replies = data.get("replies")
    if (replies != null && replies.isJsonObject) {
      comments.addAll(toList(replies.asJsonObject, level + 1))
    }

    return comment
  }

  /** Converts the [JsonObject] to a [Votable] */
  fun toVotable(obj: JsonObject): Votable? = null

  /** Converts the [JsonObject] to a [VoteStatus] */
  fun toVoteStatus(obj: JsonObject): VoteStatus? = null

  fun toSubreddit(obj: JsonElement) = Subreddit(gson.fromJson(obj.asJsonObject.get("data"), SubredditJson::class.java))

  fun toAccessTokenJson(obj: JsonElement): AccessTokenJson =
      gson.fromJson(obj, AccessTokenJson::class.java)

  fun toMeResponse(obj: JsonElement): MeResponse = gson.fromJson(obj, MeResponse::class.java)

  private fun getKind(o: JsonObject): String {
    return o.get("kind").asString
  }

  private fun getData(o: JsonObject): JsonObject {
    return o.get("data").asJsonObject
  }
}
