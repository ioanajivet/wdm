import java.util.List;


public class PatternNode {
	private String name;
	private boolean attribute;
	//private list of children
	
	public PatternNode(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

	public List<PatternNode> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}
}
