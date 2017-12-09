package me.williamhester.reddit.apis

/** A Reddit request. */
class RedditHttpRequest private constructor(
    val path: String,
    val queries: Map<String, String>?,
    val params: Map<String, String>?,
    val accessToken: String?,
    val callback: RedditHttpRequestExecutor.JsonResponseCallback
) {

  private constructor(
      builder: Builder
  ) : this(builder.path, builder.queries, builder.params, builder.accessToken, builder.callback)

  fun toBuilder() : Builder =
      Builder(path, callback).params(params).accessToken(accessToken).queries(queries)

  class Builder(
      val path: String,
      val callback: RedditHttpRequestExecutor.JsonResponseCallback
  ) {
    var queries: Map<String, String>? = null
      private set
    var params: Map<String, String>? = null
      private set
    var accessToken: String? = null
      private set

    fun queries(value: Map<String, String>?): Builder {
      queries = value
      return this
    }

    fun params(value: Map<String, String>?): Builder {
      params = value
      return this
    }

    fun accessToken(value: String?): Builder {
      accessToken = value
      return this
    }

    fun build(): RedditHttpRequest = RedditHttpRequest(this)
  }
}