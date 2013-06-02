import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Match{
	private int pre;
	private int state;	//open = 1 or closed = 0; default is open
	private Match parent;
	private Map <PatternNode, List<Match>> children = new HashMap<PatternNode, List<Match>>();
	private TPEStack st;
	private String text;
	
	public Match() {
		this.text = "";
	}
	
	public Match(int currentPre, Match top, TPEStack s) {
		this.pre = currentPre;
		this.parent = top;
		this.state = 1;	
		this.st = s;
		this.text = "";
	}
	public int getStatus() {
		return state;
	}
	
	public int getPre(){
		return pre;
	}
	
	public String getText(){
		return text;
	}
	
	public void addText(String text){
		this.text = text;
	}
	
	public Map<PatternNode, List<Match>> getChildren(){
		return children;
	}

	public void close() {
		state = 0;
	}
	
	public TPEStack getStack(){
		return st;
	}
	
	@Override
	public boolean equals(Object o){
		if (!(o instanceof Match))
			return false;
		if (((Match)o).pre == this.pre)
			return true;
		return false;
		
	}

	public void addChildMatch(PatternNode child, Match m) {
		List<Match> mat = children.get(child);
		if(mat == null)
			mat = new ArrayList<Match>();
		mat.add(m);
		children.put(child, mat);
		
	}
	
	public void removeChildMatch(PatternNode child, Match m) {
		List<Match> mat = children.get(child);
		int index = mat.indexOf(m);
		if(index == -1)
			return;
		children.get(child).remove(m);
	}

	public Match getParentMatch() {
		return parent;
	}
	
	public PatternNode getPatternNode(){
		return st.getPatternNode();
	}
	
}
