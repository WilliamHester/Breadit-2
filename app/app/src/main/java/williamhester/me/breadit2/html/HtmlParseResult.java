package williamhester.me.breadit2.html;

import java.util.List;

/** A class for the result from {@link HtmlParser}. */
public class HtmlParseResult {
  private final CharSequence spannableString;
  private final List<Anchor> links;

  HtmlParseResult(CharSequence spannableString, List<Anchor> links) {
    this.spannableString = spannableString;
    this.links = links;
  }

  public CharSequence getText() {
    return spannableString;
  }

  public List<Anchor> getLinks() {
    return links;
  }
}
