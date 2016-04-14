package fileOptimizer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import metamodel.Subsystem;
import metamodel.LogicalComponent;
import metamodel.Port;

public class JsonOptimizer {
	public static void main(String[] args){
	    
		//initialize a string to carry a JSON file content;
		String optimizedContent="";
		
		//initialize a subsystem
		Subsystem subsystem=new Subsystem();

		/*
		 * read from a JSON file <source> 
		 * the file directory can be replaced as an API later on
		*/
		JsonParser parser = new JsonParser();
		JsonElement element=null;
		try {
				FileReader fr =new FileReader("E://programs/eclipse/workspace/visualization1/src/jsons/vcspa.json");
				element = parser.parse(fr);
				fr.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		//initial set-up for making a JSON output
		GsonBuilder builder = new GsonBuilder();
		Gson gson =builder.setPrettyPrinting().create();
		
		/*
		 * read the content of the JSON source file/
		 * save to a new variables
		*/
		if (element.isJsonObject()){
			JsonObject ct=element.getAsJsonObject();
			
			//get the name of the subsystem from a file
			subsystem.setElementName(ct.get("elementName").getAsString());
            
			//get a JSON array from a file
			JsonArray contentRelations =ct.getAsJsonArray("contentRelations");
			
			//initialize a list of components
			List<LogicalComponent> listComponents = new ArrayList<LogicalComponent>();
			
			for (int i=0;i<contentRelations.size();i++){
				//get a JSON object inside a JSON array
				JsonObject ctr = contentRelations.get(i).getAsJsonObject();
				JsonObject dest=(JsonObject) ctr.get("destination");	
			
				if (dest.get("className").getAsString().equals("LC")) {
				//initialize logical component
				LogicalComponent component = new LogicalComponent();	
				
				//initialize a list of ports for each logical component
				List<Port> listPorts = new ArrayList<Port>();

				//get the name of the component from a file
				component.setElementName(dest.get("elementName").getAsString());

                //a loop to get all ports of a LogicalComponent from a file
				JsonArray contentRelationsDest =dest.getAsJsonArray("contentRelations");
				JsonObject destP=new JsonObject();
				for (int j=0;j<contentRelationsDest.size();j++){
					JsonObject ctrP = contentRelationsDest.get(j).getAsJsonObject();
					destP=(JsonObject) ctrP.get("destination");
					
					if (destP.get("className").getAsString().equals("PORT")){
				    //initialize port
				    Port port = new Port();
				    
				    //get the name of the port from a file
				    port.setElementName(destP.get("elementName").getAsString());
				    
				    //a loop to get data element of a port from a file
					JsonArray contentRelationsDestPort=destP.getAsJsonArray("contentRelations");
					int k=0;
					while(k<contentRelationsDestPort.size()){
						JsonObject ctrpElem =contentRelationsDestPort.get(k).getAsJsonObject();
						JsonObject destElem=(JsonObject)ctrpElem.get("destination");
						if (destElem.get("className").getAsString().equals("DATA-ELEM")) {
							//get the name of the data element from a file
							port.setDataElement(destElem.get("elementName").getAsString());
						    k++;
						    }
					}
					
					//initialize a JSON object that holds a IO direction of a port from a file
				    JsonObject ctrPIO=destP.get("attributes").getAsJsonObject();
				    
				    //get the IO direction of a port from a file
				    port.setIOdirection(ctrPIO.get("IODirection").getAsString());
				    
				    //add a port to list of ports
				    listPorts.add(port);
				    }
				 }
				//add a list of ports to a LogicalComponent
				component.setHasPort(listPorts);
				
				//add a LogicalComponent to a list of logical components
				listComponents.add(component);
				}
			}
			
			//add the list of logical components to a subsystem
			subsystem.setHasLC(listComponents);
			
			//make an optimized JSON output
			System.out.println(gson.toJson(subsystem));
			optimizedContent+=gson.toJson(subsystem);
		}
		
		//write the optimized JSON output to a file
		try {
		File file = new File("E://programs/eclipse/workspace/Visualization1/src/jsons/Visibility Control SPA.json");
		
		//check if the file exists
		if (!file.exists()){
			file.createNewFile();
		}

		FileWriter fw = new FileWriter (file.getAbsolutePath());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(optimizedContent);
		bw.close();
        } catch (IOException e){
           e.printStackTrace();
	    }
   }
}
