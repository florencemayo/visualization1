package componentDiagram;
import java.beans.PropertyChangeEvent;
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
				FileReader fr =new FileReader(
						"E://programs/eclipse/workspace/visualization1/src/jsons/vcspa_2.json");
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
			//componentFigure1=null;
			//PolylineConnection connection=null;
						
			//------------------------------------------------------------------------------------------
			//draw the LCs
			int x=10, y=10; int count=1;
			
			
			
			//End draw LCs
			for (int i=0;i<components.size();i++){
				//get a JSON object inside a JSON array
				JsonObject lc1 = components.get(i).getAsJsonObject();
				
				//get the name of the component from a file
				Label componentLabel = new Label(lc1.get("elementName").getAsString());
				componentLabel.setFont(componentFont);
				
				componentFigure = new UMLComponentFigure(componentLabel);
				
				Rectangle rec = new Rectangle(x,y,-1,-1); 
				x=(count % 10)*100;
				y=y+10;
				count++;
				contentsLayout.setConstraint(componentFigure, rec);
				contents.add(componentFigure);
				
	            //get ports of lc1	
				JsonArray ports =lc1.getAsJsonArray("hasPort");
				for (int j=0;j<ports.size();j++){
					JsonObject port = ports.get(j).getAsJsonObject();
					String dataElem1=port.get("dataElement").getAsString();
					
				    //second loop to check the remain LCS
					for (int k=i+1;k<components.size();k++){
						//get a JSON object inside a JSON array
						JsonObject lc2 = components.get(k).getAsJsonObject();
						
						//get the name of the component from a file
						//Label componentLabel1 = new Label(lc2.get("elementName").getAsString());
						//componentLabel1.setFont(componentFont);
						
						//componentFigure1 = new UMLComponentFigure(componentLabel1);
						
						//Rectangle rec1 = new Rectangle(x,y,-1,-1); 
						//x=(count % 10)*100;
						//y=y+10;
						//count++;
						//contentsLayout.setConstraint(componentFigure1, rec1);
						//contents.add(componentFigure1);
						
						
						//get ports of lc1
						JsonArray ports1 =lc2.getAsJsonArray("hasPort");
						for (int m=0;m<ports1.size();m++){
							JsonObject port1 = ports1.get(m).getAsJsonObject();
							String dataElem2=port1.get("dataElement").getAsString();
							 
							// if statement
							String names="lala";
						    if (dataElem1.equals(dataElem2) && !(port.equals(port1))){
						    	    
						    	    //make connection
						    	    /*connection = new PolylineConnection();
									ChopboxAnchor sourceAnchor = new ChopboxAnchor(componentFigure);
									ChopboxAnchor targetAnchor = new ChopboxAnchor(componentFigure1);
									connection.setSourceAnchor(sourceAnchor);
									connection.setTargetAnchor(targetAnchor);
									contents.add(connection);*/
						    	    names = port.get("elementName").getAsString()+"_"+
						    			port1.get("elementName").getAsString()+"_"+dataElem1;
						    	    Label attribute = new Label(names, new Image (display,UMLComponentFigure.class.getResourceAsStream("field_obj.png")));
						    	    componentFigure.getInfoFigure().add(attribute);
						    } 
						 }
					}
					
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
