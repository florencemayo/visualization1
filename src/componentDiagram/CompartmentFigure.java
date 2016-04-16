package componentDiagram;
import org.eclipse.draw2d.*;

public class CompartmentFigure extends Figure{
	
	public CompartmentFigure(){
		ToolbarLayout layout = new ToolbarLayout();
		layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
		layout.setStretchMinorAxis(false);
		layout.setSpacing(2);
		setLayoutManager(layout);
		setBorder(new CompartmentFigureBorder());
	}

}
