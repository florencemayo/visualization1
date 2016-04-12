package javafiles;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WriteToJsonFile {
	public static void main(String[] args){
	    //INITIALIZE A STRING TO copy the content;
		String content="{\n";
		
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
		
		//MANIPULATE JSON FILE
		if (element.isJsonObject()){
			JsonObject ct=element.getAsJsonObject();
			System.out.println(ct.get("elementName").getAsString());

			//concatenate name of the susbsytem
			content+='"'+"elementName"+'"'+" : "+'"'+ct.get("elementName").getAsString()+'"'+"\n";
			

			JsonArray contentRelations =ct.getAsJsonArray("contentRelations");
			for (int i=0;i<contentRelations.size();i++){
				JsonObject ctr = contentRelations.get(i).getAsJsonObject();
				JsonObject dest=(JsonObject) ctr.get("destination");
				
				
				if (dest.get("className").getAsString().equals("LC")) {
				//PRINT ALL LCs	
				System.out.println("------------*****************--------------------------");
				System.out.println(dest.get("elementName").getAsString());	
				System.out.println("------------*****************--------------------------");

				//concatenate name of the LCs


				//PRINT ALL PORTS and the direction of a port
				JsonArray contentRelationsDest =dest.getAsJsonArray("contentRelations");
				JsonObject destP=new JsonObject();
				for (int j=0;j<contentRelationsDest.size();j++){
					JsonObject ctrP = contentRelationsDest.get(j).getAsJsonObject();
					destP=(JsonObject) ctrP.get("destination");
				    if (destP.get("className").getAsString().equals("PORT")){
				    System.out.println("PORT NAME :"+destP.get("elementName").getAsString());
				    
				    //PRINT ALL DATA ELEM
					JsonArray contentRelationsDestPort=destP.getAsJsonArray("contentRelations");
					int k=0;
					while(k<contentRelationsDestPort.size()){
						JsonObject ctrpElem =contentRelationsDestPort.get(k).getAsJsonObject();
						JsonObject destElem=(JsonObject)ctrpElem.get("destination");
						if (destElem.get("className").getAsString().equals("DATA-ELEM"))
							System.out.println("DATA ELEM :"+destElem.get("elementName").getAsString());
						    k++;
					}
					
					//PRINT ALL IODIRECTION
				    JsonObject ctrPIO=destP.get("attributes").getAsJsonObject();
				    System.out.println("IODIRECTION : "+ctrPIO.get("IODirection").getAsString());
				    
					
				    }
				 }
				}
			}
		}
		
		//WRITE TO A NEW JSON FILE
		content+="}";
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
