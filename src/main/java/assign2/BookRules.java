package assign2;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;

public class BookRules {
  ISBNService theService = new AmazonISBNService();
 
  public static List<ImmutablePair<String, Integer>> sortOnTitles(List<ImmutablePair<String, Integer>>  unsortedTitles) 
  {
    return unsortedTitles.stream()
                         .sorted(comparing(ImmutablePair::getLeft))
                         .collect(toList());
  }
  
  
  public static int totalNumberOfReviews(List<ImmutablePair<String, Integer>> unsortedTitles) 
  {
    return unsortedTitles.stream()
                         .mapToInt(ImmutablePair::getRight)
                         .sum();                      
  }
  
  
  public void setISBNService(ISBNService isbnService)
  {
      theService = isbnService; 
  }
  
 
   public ImmutablePair<ImmutablePair<String, Integer>, Exception> getInfoForISBN(String a_SAMPLE_ISBN)
   {
      try{
       return ImmutablePair.of(theService.parseForISBN(a_SAMPLE_ISBN), null);
      }catch(Exception ex){
       return ImmutablePair.of(null, ex);
      }
   
   }
   

  public ImmutableTriple<List<ImmutablePair<String, Integer>>, Integer, List<Exception>> getReviewsForISBNs(
    List<String> isbnNumbers)
  {
      List<ImmutablePair<ImmutablePair<String, Integer>, Exception>> result =
        isbnNumbers.stream()
                   .map(isbn -> getInfoForISBN(isbn))
                   .collect(toList());
                   
      List<ImmutablePair<String, Integer>> infoList = 
        result.stream()
              .filter(info -> info.getLeft() != null)
              .map(ImmutablePair::getLeft)
              .collect(toList());
              
      List<Exception> ex = 
        result.stream()
              .filter(info -> info.getRight() != null)
              .map(ImmutablePair::getRight)
              .collect(toList());
     
     return ImmutableTriple.of(sortOnTitles(infoList), totalNumberOfReviews(infoList), ex);
  }
}

