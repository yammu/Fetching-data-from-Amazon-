package assign2.consoleoutput;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import assign2.AmazonISBNService;
import assign2.BookRules;
public class GetBookInfo {

  public static void main(String[] args) throws IOException {
   
             AmazonISBNService amazonIsbn = new AmazonISBNService();
             BookRules bookRules = new BookRules();
             
             List<String> isbnList=new ArrayList<String>();
             
             bookRules.setISBNService(amazonIsbn);
          
            String line = null;

            try {
                 FileReader isbnFile = new FileReader(args[0]);
                
                 BufferedReader bufferedReader = new BufferedReader(isbnFile);

                 while((line = bufferedReader.readLine()) != null) {
                    isbnList.add(line);
                }    
                System.out.println(isbnList);
                bufferedReader.close();            
            }
            catch(FileNotFoundException ex) {
                System.out.println(
                    "Unable to open file");              
              }
           System.out.println(bookRules.getReviewsForISBNs(isbnList));       
            
        }
   

  }


