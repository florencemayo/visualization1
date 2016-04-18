package componentDiagram;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UMLComponentFigureTest {
	
	public static void main(String args []){
					
		JsonParser parser = new JsonParser();
		JsonElement element=null;
		try {
				FileReader fr =new FileReader(
						"E://programs/eclipse/workspace/visualization1/src/jsons/vcspa_2.json");
				element = parser.parse(fr);
				fr.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		//creating boxes
		if (element.isJsonObject()){
			JsonObject ct=element.getAsJsonObject();
			
			//get the name of the subsystem from a file
			Display display=new Display();
			final Shell shell=new Shell(display);
			shell.setSize(1000,400);
			shell.setText(ct.get("elementName").getAsString());
			
			LightweightSystem lws=new LightweightSystem(shell);
			
			Figure contents = new Figure();
		    XYLayout contentsLayout = new XYLayout();
			contents.setLayoutManager(contentsLayout);
			
			Font componentFont = new Font (null, "Arial", 12, SWT.BOLD);
            
			//get a JSON array from a file
			JsonArray components =ct.getAsJsonArray("hasLC");
			
			UMLComponentFigure componentFigure=null;
			PolylineConnection connection=null;
						
			
			
			//a list to store all the boxes
			List<UMLComponentFigure> listBoxes=new ArrayList<UMLComponentFigure>();
			
			//make all the boxes
			int x=10, y=10; int count=1;
			for (int b=0;b<components.size();b++){
				//get a JSON object inside a JSON array
				JsonObject box = components.get(b).getAsJsonObject();
				
				//get the name of the component from a file
				Label componentLabel = new Label(box.get("elementName").getAsString());
				componentLabel.setFont(componentFont);
				
				componentFigure = new UMLComponentFigure(componentLabel);
				
				
				Rectangle rec = new Rectangle(x,y,-1,-1); 
				x=(count % 10)*100;
				y=y+10;
				count++;
				contentsLayout.setConstraint(componentFigure, rec);
				contents.add(componentFigure);
				listBoxes.add(componentFigure);
			}
			
			//check connection between lc1 and lc2
			String names="lala",names1=null;
			Label attribute=null,attribute1=null;
			
			for (int i=0;i<components.size();i++){
				//get a JSON object inside a JSON array
				JsonObject lc1 = components.get(i).getAsJsonObject();
			
	            //get ports of lc1	
				JsonArray ports =lc1.getAsJsonArray("hasPort");
				for (int j=0;j<ports.size();j++){
					JsonObject port = ports.get(j).getAsJsonObject();
					String dataElem1=port.get("dataElement").getAsString();
					
					//second loop to check the remain LCS
					for (int k=i+1;k<components.size();k++){
						//get a JSON object inside a JSON array
						JsonObject lc2 = components.get(k).getAsJsonObject();
						
						//get ports of lc1
						JsonArray ports1 =lc2.getAsJsonArray("hasPort");
						for (int m=0;m<ports1.size();m++){
							JsonObject port1 = ports1.get(m).getAsJsonObject();
							String dataElem2=port1.get("dataElement").getAsString();
							 
							// if statement
							if (dataElem1.equals(dataElem2)){
						    	    
						    	    //make connection
						    	    connection = new PolylineConnection();
									ChopboxAnchor sourceAnchor = new ChopboxAnchor(listBoxes.get(i));
									ChopboxAnchor targetAnchor = new ChopboxAnchor(listBoxes.get(k));
									connection.setSourceAnchor(sourceAnchor);
									connection.setTargetAnchor(targetAnchor);
									contents.add(connection);
						    	    //port that starts is the one that provides
									if (port.get("IOdirection").getAsString().equals("PROVIDE")) {
									    names  = port.get("elementName").getAsString()+"_"+
						    	    		 port1.get("elementName").getAsString()+"_"+
									         dataElem1;
						    	    } else {
						    	    	names  = port1.get("elementName").getAsString()+"_"+
							    	    		 port.get("elementName").getAsString()+"_"+
										         dataElem1;
						    	    }
						    	    attribute = new Label(names, new Image (display,UMLComponentFigure.class.getResourceAsStream("field_obj.png")));
						    	    listBoxes.get(i).getInfoFigure().add(attribute);
						    } 
						 }
					}
				}
			}
			
			
			//Check for data-element with no connections
			//initialize a hashmap
			HashMap hm = new HashMap(); 
			Double noConnection=0.0;
			
			for (int t=0;t<components.size();t++){
				//get a JSON object inside a JSON array
				JsonObject lc_1 = components.get(t).getAsJsonObject();
			
	            //get ports of lc1	
				JsonArray ports =lc_1.getAsJsonArray("hasPort");
				for (int z=0;z<ports.size();z++){
					JsonObject port = ports.get(z).getAsJsonObject();
					String dataElem1=port.get("dataElement").getAsString();
					
					//initiate  hashmap for a port
					String portName =port.get("elementName").getAsString();
					//hm.put(dataElem1, new Double(noConnection));
					hm.put(portName, new Double(0.0));
					
				    //second loop to check the remain LCS
					
					for (int p=0;p<components.size();p++){
						//get a JSON object inside a JSON array
						JsonObject lc_2 = components.get(p).getAsJsonObject();
						
						//get ports of lc1
						JsonArray ports1 =lc_2.getAsJsonArray("hasPort");
						if (!lc_1.equals(lc_2)){
						for (int u=0;u<ports1.size();u++){
							JsonObject port1 = ports1.get(u).getAsJsonObject();
							String dataElem2=port1.get("dataElement").getAsString();
							 
							// if statement
							if (dataElem1.equals(dataElem2)){
						    	    hm.put(portName, new Double(1.0));
						    } 
						   
						}
						//close if lc1 and lc2
					 }
					}
				}
			}
			//
			//print all the values in a hashmap
			Set set=hm.entrySet();
			Iterator i = set.iterator();
			while(i.hasNext()){
				Map.Entry me = (Map.Entry)i.next();
				String portValue = me.getKey().toString();
				if (me.getValue().equals(0.0)){
					//go through the data elements for each port
					//if you find the port with the same key as the hashmap then concatenate the its port name and its data element name
					//add the content to the figure
					for (int f=0;f<components.size();f++){
						//get a JSON object inside a JSON array
						JsonObject lc_f = components.get(f).getAsJsonObject();
					
			            //get ports of lc1	
						JsonArray ports =lc_f.getAsJsonArray("hasPort");
						for (int z1=0;z1<ports.size();z1++){
							JsonObject port = ports.get(z1).getAsJsonObject();
							String prtName  = port.get("elementName").getAsString();
							String dataElem1= port.get("dataElement").getAsString();
						    if (portValue.equals(prtName)){
						    	names1  = "FREE"+"_"+prtName+"_"+dataElem1;
					    	    attribute1 = new Label(names1, new Image (display,UMLComponentFigure.class.getResourceAsStream("field_obj.png")));
					    	    listBoxes.get(f).getInfoFigure().add(attribute1);
					    	    System.out.print(names1);
						    }
						   
						}
					}
					
				System.out.print(me.getKey().toString()+": ");
				System.out.println(me.getValue());
				}
			}
			//Creating decoration for a port that provide
			//Creating a decoration for a port that requires
			lws.setContents(contents);
			shell.open();
			while(!shell.isDisposed())
				while (!display.readAndDispatch())
					display.sleep();
		}
	}
}
