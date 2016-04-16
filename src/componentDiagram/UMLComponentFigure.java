package componentDiagram;

import org.eclipse.draw2d.*;
import org.eclipse.swt.graphics.Color;

public class UMLComponentFigure extends Figure {
 
	public static Color componentColor =new Color(null,242,242,242);
 
	private CompartmentFigure infoFigure = new CompartmentFigure();
 
	public UMLComponentFigure(Label name){
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
		setBorder(new LineBorder(ColorConstants.black,1));
		setBackgroundColor(componentColor);
		setOpaque(true);
		
		add(name);
		add(infoFigure);
	}
	
	public CompartmentFigure getInfoFigure(){
		return infoFigure;
	}
}
