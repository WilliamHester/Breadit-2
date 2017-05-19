package me.williamhester.reddit.apis

import java.util.HashMap

import me.williamhester.reddit.convert.RedditGsonConverter
import me.williamhester.reddit.messages.PostMessage
import me.williamhester.reddit.messages.VotableListMessage
import me.williamhester.reddit.models.Submission

/** Contains methods to communicate with Reddit  */
class RedditClient(
    private val redditGsonConverter: RedditGsonConverter,
    private val requestFactory: RedditHttpRequestFactory
) {

  fun getSubmissions(place: String, query: String, after: String?) {
    val queries = HashMap<String, String>()
    if (after != null) {
      queries.put("after", after)
    }
    requestFactory.createRequest(place, queries) {
      VotableListMessage(redditGsonConverter.toList<Submission>(it))
    }
  }

  fun getComments(permalink: String) {
    requestFactory.createRequest(permalink) { PostMessage(redditGsonConverter.toPost(it)) }
  }
}
