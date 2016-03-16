package assign2;

import java.io.IOException;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class AmazonISBNService implements ISBNService {
  static Integer actualReview;
  static String title;
  static Document doc;

  public static String parseTitle() throws IOException { 
    doc = Jsoup.connect("https://www.google.com/").get();
    title = doc.title();
    return title;
  }

  public static Integer parseReviewFromHtml() throws IOException {
    
      doc = Jsoup.connect("http://www.amazon.com/exec/obidos/ASIN/1576877655")
            .get();
    Element reviews = doc.getElementById("averageCustomerReviews");
    String[] reviewSplitToGetValue = reviews.text().split(" ");
    return Integer.parseInt(reviewSplitToGetValue[0]);

  }
 
   @Override
  public ImmutablePair<String, Integer> parseForISBN(String isbnNumber)
      throws IOException {

    doc = Jsoup.connect("http://www.amazon.com/exec/obidos/ASIN/" + isbnNumber)
        .get();
    title = doc.title();

    Element reviews = doc.getElementById("averageCustomerReviews");
    if(reviews == null || reviews.text().equals("Be the first to review this item"))
      actualReview = 0;
   
    else{
    String[] reviewSplitToGetValue = reviews.text().split(" ");
    actualReview = Integer.parseInt(reviewSplitToGetValue[0]
        .replaceAll(",", ""));
    }
    return ImmutablePair.of(title, actualReview);
  }
}
