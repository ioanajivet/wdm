import java.util.ArrayList;
import java.util.List;


public class PatternNode {
	private String name;
	private boolean marked = false;	//is the node is to be returned or not; default false
	private ArrayList<PatternNode> children = new ArrayList<PatternNode>();
	
	public PatternNode(String name){
		this.name = name;
	}
	
	public PatternNode(String name, boolean marked){
		this.name = name;
		this.marked = marked;
	}
	
	public void addChild(PatternNode child){
		if(child != null)
			children.add(child);
	}
	
	public String getName(){
		return name;
	}

	public List<PatternNode> getChildren() {
		return children;
	}
	
	public boolean isMarked(){
		return marked;
	}
	
	public boolean isAttribute(){
		if(name.startsWith("@"))
			return true;
		return false;
	}
	
	@Override
	public boolean equals(Object o){
		if (o instanceof PatternNode)
			return false;
		if (((PatternNode) o).name.equals(this.name))
			return true;
		return false;
	}
}
