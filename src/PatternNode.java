import java.util.ArrayList;
import java.util.List;


public class PatternNode {
	private String name;
	private boolean marked = false;			//if the node is to be part of the resulting tuple; default false
	private ArrayList<PatternNode> children = new ArrayList<PatternNode>();
	private boolean optional = false;		//if the node is optional; default false
	
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
	
	public void mark(){
		marked = true;
	}

	public void optional(){
		optional = true;
	}
	
	public boolean isOptional() {
		return optional;
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
