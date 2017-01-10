package williamhester.me.breadit2.html;

import williamhester.me.breadit2.models.Link;

/** Contains the text and the {@link Link} that it is associated with. */
public class Anchor {
  private String text;
  private Link link;

  Anchor(String text, Link link) {
    this.text = text;
    this.link = link;
  }

  public String getText() {
    return text;
  }

  public Link getLink() {
    return link;
  }
}
