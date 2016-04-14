package metamodel;

import java.util.List;

public class LogicalComponent {

	private String elementName;
	
	private List<Port> hasPort;

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public List<Port> getHasPort() {
		return hasPort;
	}

	public void setHasPort(List<Port> hasPort) {
		this.hasPort = hasPort;
	}
	
	
}
