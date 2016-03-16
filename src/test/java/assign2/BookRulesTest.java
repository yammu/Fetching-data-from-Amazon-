package assign2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.junit.Test;
import org.mockito.Mockito;

public class BookRulesTest {
  
  BookRules bookRules;
  List<ImmutablePair<String, Integer>> unsortedTitles = new ArrayList<>();
  List<ImmutablePair<String, Integer>> sortedTitles = new ArrayList<>();

  ImmutablePair<String, Integer> javaBook = ImmutablePair.of("Java Programming", 555);
  ImmutablePair<String, Integer> computerFundamental = ImmutablePair.of("Computer Fundamentals", 213);
  ImmutablePair<String, Integer> introProgramming = ImmutablePair.of("Introduction to Programming", 123);
  ImmutablePair<String, Integer> computerArchitecture = ImmutablePair.of("Computer Architecture", 200);
  
 
  String A_SAMPLE_ISBN = "1490564098";
  String ANOTHER_ISBN = "1594633665";
  String THIRD_ISBN = "149";
 
  @Test
  public void emptyListOfTitlesGivesEmptyList() {

    assertEquals(sortedTitles, BookRules.sortOnTitles(unsortedTitles));
  }

  @Test
  public void oneImmutablePairOfListGivesSameListBack() {
    unsortedTitles = Arrays.asList(javaBook);

    sortedTitles = Arrays.asList(javaBook);

    assertEquals(sortedTitles, BookRules.sortOnTitles(unsortedTitles));
  }

  @Test
  public void twoImmutablePairsOfListGivesSortedListOfThosePairs() {

    unsortedTitles = Arrays.asList(javaBook, computerFundamental);

    sortedTitles = Arrays.asList(computerFundamental, javaBook);

    assertEquals(sortedTitles, BookRules.sortOnTitles(unsortedTitles));
  }

  @Test
  public void threeImmutablePairsOfListGivesSortedListOfThreeImmutablePairs() {

    unsortedTitles = Arrays.asList(javaBook, computerFundamental, introProgramming);
    sortedTitles = Arrays.asList(computerFundamental, introProgramming, javaBook);

    assertEquals(sortedTitles, BookRules.sortOnTitles(unsortedTitles));
  }

  @Test
  public void dublicateImmutablePairsOfListGivesSortedListOfImmutablePairs() {

    unsortedTitles = Arrays.asList(javaBook, computerFundamental, javaBook);

    sortedTitles = Arrays.asList(computerFundamental, javaBook, javaBook);

    assertEquals(sortedTitles, BookRules.sortOnTitles(unsortedTitles));
  }

  @Test
  public void totalNumberOfReviewsInEmptyListIsZero() {

    assertEquals(0, BookRules.totalNumberOfReviews(unsortedTitles));
  }

  @Test
  public void totalNumberOfReviewsInOneImmutablePairOfListIsItsReviewItself() {

    unsortedTitles = Arrays.asList(javaBook);

    assertEquals(555, BookRules.totalNumberOfReviews(unsortedTitles));
  }

  @Test
  public void totalNumberOfReviewsInTwoImmutablePairOfListIsTotalOfTheirReviews() {

    unsortedTitles = Arrays.asList(javaBook, computerFundamental);

    assertEquals(768, BookRules.totalNumberOfReviews(unsortedTitles));
  }

  @Test
  public void totalNumberOfReviewsInAListHavingDublicatePairsIsTotalOfTheirReviews() {

    unsortedTitles = Arrays.asList(javaBook, computerFundamental,
        computerFundamental);

    assertEquals(981, BookRules.totalNumberOfReviews(unsortedTitles));
  }

  @Test
  public void totalNumberOfReviewsInAListHavingThreePairsIsTotalOfThoseThreeReviews() {

    unsortedTitles = Arrays.asList(javaBook, computerFundamental,
        computerArchitecture);

    assertEquals(968, BookRules.totalNumberOfReviews(unsortedTitles));
  }
  
  @Test
  public void getInfoFromIsbnWhenIsbnNumberIsTheDefaultOne() throws IOException {
    ISBNService isbnService = Mockito.mock(ISBNService.class);
    Mockito.when(isbnService.parseForISBN(A_SAMPLE_ISBN)).thenReturn(javaBook);

    BookRules bookRules = new BookRules();
    bookRules.setISBNService(isbnService);
    
    assertEquals(ImmutablePair.of(javaBook, null), bookRules.getInfoForISBN(A_SAMPLE_ISBN));
  }

    @Test 
    public void getInfoFromIsbnWhenThereIsAnErrorInIsbnNumber() throws IOException{
      ISBNService isbnService = Mockito.mock(ISBNService.class);
      RuntimeException ex = new RuntimeException("error processing  " + A_SAMPLE_ISBN); 
      Mockito.when(isbnService.parseForISBN(A_SAMPLE_ISBN)).thenThrow(ex);

      BookRules bookRules = new BookRules();
      bookRules.setISBNService(isbnService);

      assertEquals(ImmutablePair.of(null, ex), bookRules.getInfoForISBN(A_SAMPLE_ISBN));
    }
    
    @Test
    public void getInfoAndTotalReviewsForIsbnWhenGivenThreeISBNSAreValid() throws IOException {
      List<String> isbnList = new ArrayList<String>(); 
      isbnList.add(A_SAMPLE_ISBN);
      isbnList.add(ANOTHER_ISBN);
      isbnList.add(THIRD_ISBN);
      
      ISBNService isbnService = Mockito.mock(ISBNService.class);
      Mockito.when(isbnService.parseForISBN(A_SAMPLE_ISBN)).thenReturn(javaBook);
      Mockito.when(isbnService.parseForISBN(ANOTHER_ISBN)).thenReturn(computerFundamental);
      Mockito.when(isbnService.parseForISBN(THIRD_ISBN)).thenReturn(introProgramming);
      BookRules bookRules = new BookRules();
      bookRules.setISBNService(isbnService);
      
      List<ImmutablePair<String,Integer>> listOfTitleReviewPairs;
      listOfTitleReviewPairs = Arrays.asList(computerFundamental, introProgramming, javaBook);
      List<Exception> exceptionList = Arrays.asList();
      
      assertEquals(ImmutableTriple.of(listOfTitleReviewPairs, 891, exceptionList), bookRules.getReviewsForISBNs(isbnList));
      }
    
   @Test
    public void getInfoAndTotalReviewsForIsbnWhenGivenTwoISBNSAreValidAndOneIsInvalid() throws IOException {
      List<String> isbnList = new ArrayList<String>(); 
      isbnList.add(A_SAMPLE_ISBN);
      isbnList.add(ANOTHER_ISBN);
      isbnList.add(THIRD_ISBN);
      
      ISBNService isbnService = Mockito.mock(ISBNService.class);
      Mockito.when(isbnService.parseForISBN(A_SAMPLE_ISBN)).thenReturn(javaBook);
      Mockito.when(isbnService.parseForISBN(ANOTHER_ISBN)).thenReturn(computerFundamental);
      
      RuntimeException ex = new RuntimeException("error processing  " + THIRD_ISBN);
      Mockito.when(isbnService.parseForISBN(THIRD_ISBN)).thenThrow(ex);
      
      BookRules bookRules = new BookRules();
      bookRules.setISBNService(isbnService);
      
      List<ImmutablePair<String,Integer>> listOfTitleReviewPairs;
      listOfTitleReviewPairs = Arrays.asList(computerFundamental, javaBook);
      List<Exception> exceptionList = Arrays.asList(ex);
           
      assertEquals(ImmutableTriple.of(listOfTitleReviewPairs, 768, exceptionList), bookRules.getReviewsForISBNs(isbnList));
    }
}
  
   