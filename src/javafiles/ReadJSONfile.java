package javafiles; 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJSONfile {
  public static void main(String [] args){
	  
	  try {
		  // Content to be filled to a java files
		  // set the name of the package
		  
		  String content = "package ";
		  
		  //make an instance of a class, get the name of the current package
		  ReadJSONfile o= new ReadJSONfile();
		  String packName=o.getClass().getPackage().getName();
		  content+=packName+";\n\n\n";
		  
		  // set the name of the class
		  content+="public class ComponentDiagram {\n";
		  
		  File file =new File("E://programs/eclipse/Visualization1/src/javafiles/ComponentDiagram.java");
	      
		  //if file doesn't exists, then create it
		  if (!file.exists()){
			  file.createNewFile();
		  }
		  
	      JSONParser parser = new JSONParser();
	  	  try {
			    Object obj = parser.parse(new FileReader("E://programs/eclipse/Visualization1/src/jsons/manifest.txt"));
			    JSONObject jsonObject = (JSONObject) obj;
			    
			    // begin component diagram symbol
			    content+="\n@startuml\n";
			    
			    String name = (String) jsonObject.get("SEDS"); //.get("name");
			    content+="["+name+"]";
			    
			    // closing a component diagram
			    content+="\n@enduml\n";
				  } catch (FileNotFoundException e) {
					  e.printStackTrace();
				  } catch (IOException e) {
					  e.printStackTrace();
				  } catch (ParseException e) {
					  e.printStackTrace();
				  }
	  	  
	  	  // Finishing up adding last closing bracket to a content
	      content+="}";
	      
	  	  FileWriter fw = new FileWriter (file.getAbsolutePath());
	  	  BufferedWriter bw = new BufferedWriter(fw);
	  	  bw.write(content);
	  	  bw.close();
	  	  
	  	  System.out.println("Done");
	     
	   } catch(IOException e){
		  e.printStackTrace();
	    }
 }
}
 
