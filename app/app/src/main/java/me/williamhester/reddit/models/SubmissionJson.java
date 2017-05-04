package me.williamhester.reddit.models;

import java.util.List;

/**
 * A class to hold Submission data. Has no getters or setters, as it should be used entirely as a
 * struct to parse the data from the server using Gson then to be put into real models.
 */
public class SubmissionJson {
  String domain;
  String subreddit;
  String subreddit_id;
  String id;
  String name;
  String author;
  String thumbnail;
  String permalink;
  String url;
  String title;
  String post_hint;
  String selftext_html;
  String link_flair_text;
  String author_flair_text;
  String distinguished;
  Edited edited;
  Preview preview;
  boolean archived;
  boolean over_18;
  boolean hidden;
  boolean saved;
  boolean stickied;
  boolean is_self;
  boolean locked;
  boolean hide_score;
  boolean visited;
  int gilded;
  int score;
  int created;
  int created_utc;
  int num_comments;
  int edited_utc;

  static class Preview {
    List<ImageData> images;
  }

  static class ImageData {
    Image source;
    List<Image> resolutions;
    Variants variants;
  }

  static class Image {
    String url;
    int width;
    int height;
  }

  static class Variants {
    ImageData mp4;
  }
}
