package javafiles;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class JsonSimpleExample {
   @SuppressWarnings("unchecked")
public static void main(String[] args) {
	   
	   JSONObject obj = new JSONObject();
	   obj.put("name","mkyong.com");
	   obj.put("age", new Integer(100));
	   
	   JSONArray list = new JSONArray();
	   list.add("Hello flo");
	   list.add("Hi Mayo");
	   list.add("Late dinner");
	   
	   obj.put("messages", list);
	   
	   try {
		   FileWriter file = new FileWriter("E://programs/eclipse/Visualization1/src/jsons/test.json");
		   file.write(obj.toJSONString());
		   file.flush();
		   file.close();
		   
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
	   
	   System.out.print(obj);
	   
   }
}
