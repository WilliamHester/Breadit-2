package me.williamhester.reddit.messages

import me.williamhester.reddit.models.Subreddit

/** The broadcast message for a loaded subreddit. */
class SubredditMessage(val subreddits: List<Subreddit>)