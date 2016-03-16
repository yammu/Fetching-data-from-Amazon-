package assign2;

import java.io.IOException;

import org.apache.commons.lang3.tuple.ImmutablePair;

public interface ISBNService {

  public  ImmutablePair<String, Integer> parseForISBN(String isbnNumber) throws IOException;
} 