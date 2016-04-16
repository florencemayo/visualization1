package componentDiagram;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.PointList;
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
				FileReader fr =new FileReader("E://programs/eclipse/workspace/visualization1/src/jsons/vcspa.json");
				element = parser.parse(fr);
				fr.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		if (element.isJsonObject()){
			JsonObject ct=element.getAsJsonObject();
			
			//get the name of the subsystem from a file
			//subsystem.setElementName(ct.get("elementName").getAsString());
			Display display=new Display();
			final Shell shell=new Shell(display);
			shell.setSize(400,400);
			shell.setText("UMLComponentFigure");
			LightweightSystem lws=new LightweightSystem(shell);
            
			//get a JSON array from a file
			JsonArray contentRelations =ct.getAsJsonArray("contentRelations");
			
			for (int i=0;i<contentRelations.size();i++){
				//get a JSON object inside a JSON array
				JsonObject ctr = contentRelations.get(i).getAsJsonObject();
				JsonObject dest=(JsonObject) ctr.get("destination");	
				
				if (dest.get("className").getAsString().equals("LC")) {
				
				
				Figure contents = new Figure();
				XYLayout contentsLayout = new XYLayout();
				contents.setLayoutManager(contentsLayout);
					
				//get the name of the component from a file
				Font componentFont = new Font (null, "Arial", 12, SWT.BOLD);
				Label componentLabel = new Label(dest.get("elementName").getAsString());
				componentLabel.setFont(componentFont);
				
				UMLComponentFigure componentFigure = new UMLComponentFigure(componentLabel);
				
				

                //a loop to get all ports of a LogicalComponent from a file
				JsonArray contentRelationsDest =dest.getAsJsonArray("contentRelations");
				JsonObject destP=new JsonObject();
				for (int j=0;j<contentRelationsDest.size();j++){
					JsonObject ctrP = contentRelationsDest.get(j).getAsJsonObject();
					destP=(JsonObject) ctrP.get("destination");
					
					if (destP.get("className").getAsString().equals("PORT")){
				   
				    //get the name of the port from a file
				    //port.setElementName(destP.get("elementName").getAsString());
				    
				    //Label attribute =new Label("DATA-ELEM", new Image (display,
				    Label portName =new Label(destP.get("elementName").getAsString(), new Image (display,
					        UMLComponentFigure.class.getResourceAsStream("field_obj.png")));
					componentFigure.getInfoFigure().add(portName);
				    
				    //a loop to get data element of a port from a file
					JsonArray contentRelationsDestPort=destP.getAsJsonArray("contentRelations");
					int k=0;
					while(k<contentRelationsDestPort.size()){
						JsonObject ctrpElem =contentRelationsDestPort.get(k).getAsJsonObject();
						JsonObject destElem=(JsonObject)ctrpElem.get("destination");
						if (destElem.get("className").getAsString().equals("DATA-ELEM")) {
							//get the name of the data element from a file
							//port.setDataElement(destElem.get("elementName").getAsString());
							
							//label for data elem
							Label dataElemName =new Label(destElem.get("elementName").getAsString(), new Image (display,
							        UMLComponentFigure.class.getResourceAsStream("field_obj.png")));
							componentFigure.getInfoFigure().add(dataElemName);
						    
							k++;
						    }
					}
					
					//initialize a JSON object that holds a IO direction of a port from a file
				    JsonObject ctrPIO=destP.get("attributes").getAsJsonObject();
				    
				    //get the IO direction of a port from a file
				    //port.setIOdirection(ctrPIO.get("IODirection").getAsString());
				    
				   
				    }
				 }
				int x=i*10, y=i*10;
				Rectangle rec = new Rectangle(x,y,-1,-1);
				contentsLayout.setConstraint(componentFigure, rec);
				/*Creating connection
				PolylineConnection connection = new PolylineConnection();
				ChopboxAnchor sourceAnchor = new ChopboxAnchor(componentFigure);
				ChopboxAnchor targetAnchor = new ChopboxAnchor(componentFigure);
				connection.setSourceAnchor(sourceAnchor);
				connection.setTargetAnchor(targetAnchor);
				*/
				
				//Creating decoration for a port that provide
				//Creating a decoration for a port that requires
				
				contents.add(componentFigure);
				//contents.add(connection);
				lws.setContents(contents);
				shell.open();
				while(!shell.isDisposed())
					while (!display.readAndDispatch())
						display.sleep();
				}
			}		
		}
		
		
	}
}
