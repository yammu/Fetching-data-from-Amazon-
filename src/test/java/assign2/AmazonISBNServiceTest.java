package assign2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class AmazonISBNServiceTest {
  
  ISBNService bookISBN = new AmazonISBNService();
  String unbroken_ISBN= "1502326582";
  String indi_ISBN = "0061950726";
  String bigday_ISBN = "1481400975";

  @Test
  public void getTheTitleFromHtmlPage() throws IOException {
    String title ="Google";    
    assertEquals(title,AmazonISBNService.parseTitle());
  }
  
  
  @Test
  public void getTheReviewOfBookFromHtmlPage() throws IOException {
    Integer review = 6;
    assertEquals(review >= 6, AmazonISBNService.parseReviewFromHtml() >= 6);
  }
  
  
  @Test
  public void checkTitleIsValidReturnedFromImmutablePair() throws IOException {
    String isbnNumber = "1576877655";
    String title = "Indi Surfs: Chris Gorman: 9781576877654: Amazon.com: Books";
    assertEquals(title, bookISBN.parseForISBN(isbnNumber).left);
  }
  
  
  @Test
  public void checkReviewIsValidReturnedFromImmutalbePair() throws IOException {
    String isbnNumber = "1576877655";
    Integer reviews = 6;
    assertEquals(reviews >= 6, bookISBN.parseForISBN(isbnNumber).right >= 6);
  }
  
  
  @Test
  public void checkForLargerReviewsRemovingCommaInBetween() throws IOException {
    String isbnNumber = "0525953094";
    Integer reviews = 8619;
    assertEquals(reviews >= 8619, bookISBN.parseForISBN(isbnNumber).right >= 8619);
  }
  
  
  @Test
  public void returnReviewAsZeroIfThereisNoReviewWritten() throws IOException {
    String isbnNumber = "0062391194";
    Integer reviews = 0;
    assertEquals(reviews >= 0, bookISBN.parseForISBN(isbnNumber).right >= 0);
  }
  
  
  @Test
  public void returnReviewAsZeroIfReviewIsMissing() throws IOException {
    String isbnNumber = "0374129231";
    Integer reviews = 0;
    assertEquals(reviews >= 0, bookISBN.parseForISBN(isbnNumber).right >= 0);
  }
  
  
  @Test
  public void invalidIsbnThrowsAnException(){
    String isbnNumber = "1575";
    try{
      bookISBN.parseForISBN(isbnNumber);
      fail("Expected Exception for invalid ISBN");
    }catch(IOException ex){
    assertTrue(true);
    }
  }
  
  
 @Test
  public void checkTotalReviewAreValidReturnedFromTheImmutableTriple() {
    List<String> isbnList = new ArrayList<String>(); 
    isbnList = Arrays.asList(bigday_ISBN, unbroken_ISBN, indi_ISBN) ;
   
    BookRules bookRules = new BookRules();
    Integer totalReview = 104;
         
    assertEquals(totalReview >= 104, bookRules.getReviewsForISBNs(isbnList).middle >= 104);
      }
 
}
