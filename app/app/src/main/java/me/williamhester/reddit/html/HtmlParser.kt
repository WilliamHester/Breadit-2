package me.williamhester.reddit.html

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import android.text.style.LeadingMarginSpan
import android.text.style.QuoteSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.SuperscriptSpan
import android.text.style.TypefaceSpan
import android.text.style.UnderlineSpan

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode

import java.util.ArrayList

import me.williamhester.reddit.models.Link
import me.williamhester.reddit.ui.ContentClickCallbacks
import me.williamhester.reddit.ui.VotableCallbacks
import me.williamhester.reddit.ui.text.LinkSpan
import me.williamhester.reddit.ui.text.SpoilerSpan

/**
 * This class parses unescaped HTML into a Spannable String. It also produces a list of anchors that
 * were found during the creation of the Spannable String.
 */
class HtmlParser(private val callbacks: ContentClickCallbacks) {

  private val anchors = ArrayList<Anchor>()

  /**
   * Parses the HTML of the String and returns an [HtmlParseResult]. The result contains the
   * [SpannableStringBuilder] that can be used to display the parsed text and a list of
   * [Anchor]s that represents every link and the text that is displayed in it.
   */
  fun parseHtml(html: String): HtmlParseResult {
    val document = Jsoup.parse(html)
    val sb = SpannableStringBuilder()
    generateString(document, sb, anchors)
    while (sb.isNotEmpty() && (sb[0] == '\n' || sb[0] == ' ')) {
      sb.delete(0, 1)
    }
    return HtmlParseResult(sb, anchors)
  }

  private fun generateString(
      node: Node, ssb: SpannableStringBuilder, anchors: MutableList<Anchor>): SpannableStringBuilder {
    if (node is TextNode) {
      ssb.append(node.text())
    }

    insertNewLine(node, ssb)

    val children = node.childNodes()
    for (n in children) {
      ssb.append(generateString(n, SpannableStringBuilder(), anchors))
    }

    if (ssb.length > 0) {
      val span = getSpanFromTag(node, ssb, anchors)
      if (span is LeadingMarginSpan) {
        ssb.setSpan(
            getSpanFromTag(node, ssb, anchors), 1, ssb.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      } else {
        ssb.setSpan(
            getSpanFromTag(node, ssb, anchors), 0, ssb.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      }
    }

    return ssb
  }

  private fun getSpanFromTag(node: Node, ssb: SpannableStringBuilder, anchors: MutableList<Anchor>): Any? {
    if (node !is Element) {
      return null
    }
    val tag = node.tag().name.toLowerCase()
    when (tag) {
      "code" -> return TypefaceSpan("monospace")
      "del" -> return StrikethroughSpan()
      "h1" -> {
        ssb.setSpan(RelativeSizeSpan(1.2f), 0, ssb.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return StyleSpan(Typeface.BOLD)
      }
      "strong" -> return StyleSpan(Typeface.BOLD)
      "em" -> return StyleSpan(Typeface.ITALIC)
      "blockquote" -> return QuoteSpan(Color.rgb(246, 128, 38))
      "sup" -> return SuperscriptSpan()
      "a" -> {
        val url = node.attr("href")
        if (url == "/spoiler") {
          return SpoilerSpan()
        }
        if (url == "/s" || url == "#s") {
          val spoiler = node.attr("title")
          ssb.append(' ')
          ssb.append(spoiler)
          ssb.setSpan(SpoilerSpan(), ssb.length - spoiler.length, ssb.length,
              Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
          return UnderlineSpan()
        }
        val link = Link(url)
        anchors.add(Anchor(ssb.toString(), link))
        return LinkSpan(link, callbacks)
      }
      "li" -> return BulletSpan(BulletSpan.STANDARD_GAP_WIDTH * 4, 0xfff68026.toInt())
    }
    return null
  }

  private fun insertNewLine(node: Node, sb: SpannableStringBuilder) {
    if (node is Element) {
      if (node.tagName().equals("li", ignoreCase = true)
          && (node.childNode(0) !is Element
          || !(node.childNode(0) as Element).tagName().equals("p", ignoreCase = true))) {
        sb.append("\n")
      } else if (node.tagName().equals("p", ignoreCase = true)
          || node.tagName().equals("pre", ignoreCase = true)
          || node.tagName().equals("h1", ignoreCase = true)) {
        sb.append("\n")
      }
    }
  }
}

