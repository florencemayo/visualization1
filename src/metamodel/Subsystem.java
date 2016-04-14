package metamodel;

import java.util.List;

public class Subsystem {
	
	private String elementName;
	
	private List<LogicalComponent> hasLC;

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public List<LogicalComponent> getHasLC() {
		return hasLC;
	}

	public void setHasLC(List<LogicalComponent> hasLC) {
		this.hasLC = hasLC;
	}
	
 
}
