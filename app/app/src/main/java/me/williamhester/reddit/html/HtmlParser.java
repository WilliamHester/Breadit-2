package me.williamhester.reddit.html;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.ArrayList;
import java.util.List;

import me.williamhester.reddit.models.Link;
import me.williamhester.reddit.ui.ContentClickCallbacks;
import me.williamhester.reddit.ui.VotableCallbacks;
import me.williamhester.reddit.ui.text.LinkSpan;
import me.williamhester.reddit.ui.text.SpoilerSpan;

/**
 * This class parses unescaped HTML into a Spannable String. It also produces a list of anchors that
 * were found during the creation of the Spannable String.
 */
public class HtmlParser {

  private final List<Anchor> anchors = new ArrayList<>();
  private final ContentClickCallbacks callbacks;

  public HtmlParser(ContentClickCallbacks callbacks) {
    this.callbacks = callbacks;
  }

  /**
   * Parses the HTML of the String and returns an {@link HtmlParseResult}. The result contains the
   * {@link SpannableStringBuilder} that can be used to display the parsed text and a list of
   * {@link Anchor}s that represents every link and the text that is displayed in it.
   */
  public HtmlParseResult parseHtml(@NonNull String html) {
    Document document = Jsoup.parse(html);
    SpannableStringBuilder sb = new SpannableStringBuilder();
    generateString(document, sb, anchors);
    while (sb.length() > 0 && (sb.charAt(0) == '\n' || sb.charAt(0) == ' ')) {
      sb.delete(0, 1);
    }
    return new HtmlParseResult(sb, anchors);
  }

  private SpannableStringBuilder generateString(
      Node node, SpannableStringBuilder ssb, List<Anchor> anchors) {
    if (node instanceof TextNode) {
      ssb.append(((TextNode) node).text());
    }

    insertNewLine(node, ssb);

    List<Node> children = node.childNodes();
    for (Node n : children) {
      ssb.append(generateString(n, new SpannableStringBuilder(), anchors));
    }

    if (ssb.length() > 0) {
      Object span = getSpanFromTag(node, ssb, anchors);
      if (span instanceof LeadingMarginSpan) {
        ssb.setSpan(
            getSpanFromTag(node, ssb, anchors), 1, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      } else {
        ssb.setSpan(
            getSpanFromTag(node, ssb, anchors), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
    }

    return ssb;
  }

  private Object getSpanFromTag(Node node, SpannableStringBuilder ssb, List<Anchor> anchors) {
    if (!(node instanceof Element)) {
      return null;
    }
    Element element = (Element) node;
    String tag = element.tag().getName().toLowerCase();
    switch (tag) {
      case "code":
        return new TypefaceSpan("monospace");
      case "del":
        return new StrikethroughSpan();
      case "h1":
        ssb.setSpan(new RelativeSizeSpan(1.2F), 0, ssb.length(),
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      case "strong":
        return new StyleSpan(Typeface.BOLD);
      case "em":
        return new StyleSpan(Typeface.ITALIC);
      case "blockquote":
        return new QuoteSpan(Color.rgb(246, 128, 38));
      case "sup":
        return new SuperscriptSpan();
      case "a":
        String url = node.attr("href");
        if (url.equals("/spoiler")) {
          return new SpoilerSpan();
        }
        if (url.equals("/s") || url.equals("#s")) {
          String spoiler = node.attr("title");
          ssb.append(' ');
          ssb.append(spoiler);
          ssb.setSpan(new SpoilerSpan(), ssb.length() - spoiler.length(), ssb.length(),
              Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
          return new UnderlineSpan();
        }
        Link link = new Link(url);
        anchors.add(new Anchor(ssb.toString(), link));
        return new LinkSpan(link, callbacks);
      case "li":
        return new BulletSpan(BulletSpan.STANDARD_GAP_WIDTH * 4, 0xfff68026);
    }
    return null;
  }

  private static void insertNewLine(Node node, SpannableStringBuilder sb) {
    if (node instanceof Element) {
      if (((Element) node).tagName().equalsIgnoreCase("li")
          && (!(node.childNode(0) instanceof Element)
          || !((Element) node.childNode(0)).tagName().equalsIgnoreCase("p"))) {
        sb.append("\n");
      } else if (((Element) node).tagName().equalsIgnoreCase("p")
          || ((Element) node).tagName().equalsIgnoreCase("pre")
          || ((Element) node).tagName().equalsIgnoreCase("h1")) {
        sb.append("\n");
      }
    }
  }
}

