package componentDiagram;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.Insets;

public class CompartmentFigureBorder extends AbstractBorder {

	@Override
	public Insets getInsets(IFigure figure) {
		return new Insets(1,0,0,0);
	}

	@Override
	public void paint(IFigure figure, Graphics graphics, Insets insets) {
		graphics.drawLine(
				          getPaintRectangle(figure,insets).getTopLeft(),
				          tempRect.getTopRight()
				          );
	}
}
