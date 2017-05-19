package me.williamhester.reddit.html

import me.williamhester.reddit.models.Link

/** Contains the text and the [Link] that it is associated with.  */
class Anchor internal constructor(val text: String, val link: Link)
