package me.williamhester.reddit.html

/** A class for the result from [HtmlParser].  */
class HtmlParseResult internal constructor(val text: CharSequence, val links: List<Anchor>)
