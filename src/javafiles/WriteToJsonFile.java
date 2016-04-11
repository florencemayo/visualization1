package javafiles;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WriteToJsonFile {
	public static void main(String[] args){
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
			JsonArray contentRelations =ct.getAsJsonArray("contentRelations");
			for (int i=0;i<contentRelations.size();i++){
				JsonObject ctr = contentRelations.get(i).getAsJsonObject();
				JsonObject dest=(JsonObject) ctr.get("destination");
				
				
				if (dest.get("className").getAsString().equals("LC")) {
				//PRINT ALL LCs	
				System.out.println("------------*****************--------------------------");
				System.out.println(dest.get("elementName").getAsString());	
				System.out.println("------------*****************--------------------------");
				//PRINT ALL PORTS and the direction of a port
				JsonArray contentRelationsDest =dest.getAsJsonArray("contentRelations");
				JsonObject destP=new JsonObject();
				for (int j=0;j<contentRelationsDest.size();j++){
					JsonObject ctrP = contentRelationsDest.get(j).getAsJsonObject();
					destP=(JsonObject) ctrP.get("destination");
				    if (destP.get("className").getAsString().equals("PORT")){
				    System.out.println(destP.get("elementName").getAsString());
				    //System.out.println(destP.get("IODirection").getAsString());
				    }
				   				
					//PRINT ALL DATA ELEM
					/*JsonArray contentRelationsDestPort=destP.getAsJsonArray("contentRelations");
					for (int k=0; k<contentRelationsDestPort.size();k++){
						JsonObject ctrpElem =contentRelationsDestPort.get(k).getAsJsonObject();
						JsonObject destElem=(JsonObject)ctrpElem.get("destination");
						if (destElem.get("classType").getAsString().equals("DATA-ELEM"))
							System.out.println(destElem.get("elementName").getAsString());
					}*/
				 }
				}
			}
		}
	}
}
