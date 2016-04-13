package javafiles;

//import java.util.ArrayList;
import java.util.List;

public class HasLC {

	private String elementName;
	
	private List<HasPort> hasPort;
	//=new ArrayList<HasPort>();

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public List<HasPort> getHasPort() {
		return hasPort;
	}

	public void setHasPort(List<HasPort> hasPort) {
		this.hasPort = hasPort;
	}
	
	
}
