package componentDiagram;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class MyListener implements MouseListener, MouseMotionListener  {

	Figure figure;
	Point location;
	
	public MyListener(Figure figure) {
		this.figure=figure;
		figure.addMouseListener(this);
		figure.addMouseMotionListener(this);}
	
	@Override
	public void mouseDragged(MouseEvent me) {
	  Point newLocation =me.getLocation();
	  if (location==null || newLocation==null)
		  return;
	  
	  // calculate offset wrt last location
	  Dimension offset = newLocation.getDifference(location);
	  if (offset.width==0 && offset.height==0)
		  return;
	  
	  // exchange location
	  location = newLocation;
	  
	  // old Bounds are dirty
	  figure.getUpdateManager().addDirtyRegion(figure.getParent(), figure.getBounds());
	  
	  // translate figure
	  figure.translate(offset.width, offset.height);
	  
	  // new bounds are dirty
	  figure.getUpdateManager().addDirtyRegion(figure.getParent(), figure.getBounds());
	  
	  // new Bounds : set parent constraint
	  figure.getParent().getLayoutManager().setConstraint(figure, figure.getBounds());
	  
	  me.consume();
			  
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseHover(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDoubleClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent me) {
		location=me.getLocation();
		me.consume();
	}

	@Override
	public void mouseReleased(MouseEvent me) {
	   if (location==null)
		   return;
	   location=null;
	   me.consume();
	}

}
