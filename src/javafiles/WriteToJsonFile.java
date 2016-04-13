package javafiles;
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

public class WriteToJsonFile {
	public static void main(String[] args){
	    
		//INITIALIZE A STRING TO copy the content;
		String content="";
		
		//Take an example
		Example subsystem=new Example();

		//READ FROM A FILE
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
		
		//set-up for creating a JSON output
		GsonBuilder builder = new GsonBuilder();
		Gson gson =builder.create();
		
		//MANIPULATE JSON FILE
		if (element.isJsonObject()){
			JsonObject ct=element.getAsJsonObject();
			//System.out.println(ct.get("elementName").getAsString());

			subsystem.setElementName(ct.get("elementName").getAsString());
            
			JsonArray contentRelations =ct.getAsJsonArray("contentRelations");
			
			//Initiate a list of components, and list of ports
			List<HasLC> listComponents = new ArrayList<HasLC>();
			
			
			//Open bracket for the LCs
			for (int i=0;i<contentRelations.size();i++){
				JsonObject ctr = contentRelations.get(i).getAsJsonObject();
				JsonObject dest=(JsonObject) ctr.get("destination");
				
			
				if (dest.get("className").getAsString().equals("LC")) {
				//Initialize hasLC
				HasLC component = new HasLC();	
				
				//each LC has its own ports
				List<HasPort> hasPorts = new ArrayList<HasPort>();

			    //set the name of the component and add lc to a list of component
			    component.setElementName(dest.get("elementName").getAsString());

                //PRINT ALL PORTS and the direction of a port
				JsonArray contentRelationsDest =dest.getAsJsonArray("contentRelations");
				JsonObject destP=new JsonObject();
				for (int j=0;j<contentRelationsDest.size();j++){
					JsonObject ctrP = contentRelationsDest.get(j).getAsJsonObject();
					destP=(JsonObject) ctrP.get("destination");
					
					if (destP.get("className").getAsString().equals("PORT")){
				    //System.out.println("PORT NAME :"+destP.get("elementName").getAsString());
				    	
				    //Initialize hasPorts
				    HasPort port = new HasPort();
				    
				    //add name of the port
				    port.setElementName(destP.get("elementName").getAsString());
				    
				    //loop through the list to get only a data elem of the port
					JsonArray contentRelationsDestPort=destP.getAsJsonArray("contentRelations");
					int k=0;
					while(k<contentRelationsDestPort.size()){
						JsonObject ctrpElem =contentRelationsDestPort.get(k).getAsJsonObject();
						JsonObject destElem=(JsonObject)ctrpElem.get("destination");
						if (destElem.get("className").getAsString().equals("DATA-ELEM")) {
							//add the name of the data element
							port.setDataElement(destElem.get("elementName").getAsString());
						    k++;
						    }
					}
					
					//PRINT ALL IODIRECTION
				    JsonObject ctrPIO=destP.get("attributes").getAsJsonObject();
				    
				    //add the name of the iodirection to the port
				    port.setIOdirection(ctrPIO.get("IODirection").getAsString());
				    
				    //add a port to a list, list of ports in a single lc
				    hasPorts.add(port);
				    }
				 }
				//add all ports belong to the lc
				component.setHasPort(hasPorts);
				
				//add an lc to a list of components
				listComponents.add(component);
				}
			}
			
			//add the list of lcs to a subsystem
			subsystem.setHasLC(listComponents);
			
			//Produce an output of a json
			System.out.println(gson.toJson(subsystem));
			content+=gson.toJson(subsystem);
			
			
		}
		
		//WRITE TO A NEW JSON FILE
		try {
		File file = new File("E://programs/eclipse/workspace/Visualization1/src/jsons/Visibility Control SPA.json");
		
		//check if the file exists
		if (!file.exists()){
			file.createNewFile();
		}

		FileWriter fw = new FileWriter (file.getAbsolutePath());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();

		System.out.println("DONE");
	} catch (IOException e){
      e.printStackTrace();
	}


	}
}
