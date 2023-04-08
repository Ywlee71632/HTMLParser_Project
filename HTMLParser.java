//TODO: Doc comment with your name and a short description of the file go here
/**
 * Checks if the tags in HTML file are correcly matching
 * @author Yewon Lee (editted the file written by Heather Pon-barry, Alyx Burns, and Tayloe)
 */
import java.util.Scanner;
import java.io.*;

public class HTMLParser {

  //TODO: Rename these with meaningful names!
  private StackLL<String> tagStack = new StackLL<String>();
  private QueueLL<String> tagQueue = new QueueLL<String>();


  //TODO: Do you need a constructor? What should it do?
  //I wouldn't need a constructor since I already created instance variable above. 
  //My instance variables will create stack and queue for my instances from main method in order to run the program. 

  //TODO: Write a doc comment for this method, and modify the code
  /**
   * Extracts only the tags from the HTML file
   * @param filename is the file where the tags will be extracted from
   * @throws FileNotFoundException
   */
  public void getTagsFromFile(String filename) throws FileNotFoundException{

	//Code to open a file, then read it through the Scanner
	File infile = new File(filename);
	Scanner dataIn = new Scanner(infile);
	String expression;

	//Go through each line in the file
	while (dataIn.hasNextLine()) {
		//Get the next line
		expression = dataIn.nextLine();

	  	//Split breaks up a String based on the parameter passed in, and creats a String array
	  	//Breaks things up based on a '<'
		String[] line = expression.split("<");
 	  	//Loop through each String from the array created from the input
	  	for(int tagIndex = 0; tagIndex < line.length; tagIndex++){

			//Break each String based on a '>'
			int bIndex = line[tagIndex].indexOf(">");
            if (bIndex > 0) {
				String tags = line[tagIndex].substring(0, bIndex);
	            // enqueue only the tags to the queue
				tagQueue.enqueue(tags);

	        }
		}
	}

	//Close the Scanner when done; optional, but prevents a warning
		dataIn.close();
	}

  //TODO: More methods go here!

	/**
	 * Delete the complete pairs of tags
	 */
	public void deletePairs(){

		//loop until tagQueue gets empty (all crossed out)
		while(!tagQueue.isEmpty()){
			//Get the first element from the queue
			String dequeued = tagQueue.dequeue();

			if (dequeued.charAt(0) == '/'){
				//if the first element starts with '/', then push it to the stack
				if(tagStack.isEmpty()) {
					tagStack.push(dequeued);
				}
				//compare the spelling of dequeued String and the first String element from the stack
				else if(!(tagStack.isEmpty()) && dequeued.substring(1, dequeued.length()).toUpperCase().equals(tagStack.peek().toUpperCase())){
					//Cross the top tag out since it is a complete pair with dequeued String
					tagStack.pop();
				}
			}

			else {
			
			// if dequeued String does not start with '/', push it to the stack
			tagStack.push(dequeued);

			}
		}
	}

	/**
	 * Check if the file contains complete pairs of tags only
	 * @return boolean that represents whether the file contains complete pairs of tags or not
	 */
	public boolean isMatching(){
		// Check if the tagStack is empty (if all tags are crossed out since they are complete pairs)
		if (tagStack.isEmpty()){
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Method Tester
	 * @param args are HTML files
	 * @throws FileNotFoundException
	 */  
	public static void main(String[] args) throws FileNotFoundException{

		HTMLParser parser1 = new HTMLParser();
		HTMLParser parser2 = new HTMLParser();
		HTMLParser parser3 = new HTMLParser();

		//Extract tags from the HTML file
		parser1.getTagsFromFile(args[1]);
		//Delete complete pairs
		parser1.deletePairs();

		//Check if the file contains complete pairs only
		if (parser1.isMatching()) {
			System.out.println(args[1] + ": No errors found in html file");
		}
		else {
			System.out.println(args[1] +": Error found");
		}

		parser2.getTagsFromFile(args[2]);
		parser2.deletePairs();
		if (parser2.isMatching()) {
			System.out.println(args[2] + ": No errors found in html file");
		}
		else {
			System.out.println(args[2] +": Error found");
		}

		parser3.getTagsFromFile(args[4]);
		parser3.deletePairs();
		if (parser3.isMatching()) {
			System.out.println(args[4] + ": No errors found in html file");
		}
		else {
			System.out.println(args[4] +": Error found");
		}

  }
}
