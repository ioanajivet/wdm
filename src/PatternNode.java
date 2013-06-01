import java.util.ArrayList;
import java.util.List;


public class PatternNode {
	private String name;
	private String text;
	private boolean marked = false;			//if the node is to be part of the resulting tuple; default false
	private boolean optional = false;		// If there is an optional edge between this node and its parent(default:false)
	private String wildcard = null;
	private ArrayList<PatternNode> children = new ArrayList<PatternNode>();
	
	public PatternNode(String name){
		this.name = name;
	}
	
	public PatternNode(String name, boolean marked, boolean optional, String wildcard){
		this.name = name;
		this.marked = marked;
		this.setOptional(optional);
		this.setWildcard(wildcard);
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
	
	@Override
	public boolean equals(Object o){
		if (o instanceof PatternNode)
			return false;
		if (((PatternNode) o).name.equals(this.name))
			return true;
		return false;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public String getWildcard() {
		return wildcard;
	}

	public void setWildcard(String wildcard) {
		this.wildcard = wildcard;
	}

}
