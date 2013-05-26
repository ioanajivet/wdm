import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class TPEStack {
	private	PatternNode p;
	private Stack <Match> matches;
	private	TPEStack spar;
	private ArrayList<TPEStack> childStacks;
	
	public TPEStack(PatternNode p, Stack<Match> stack, TPEStack parent){
		this.p = p;
		
		if(stack == null)
			this.matches = new Stack<Match>();
		else
			this.matches = stack;
		
		this.spar = parent;
		this.childStacks = new ArrayList<TPEStack>();
	}
	
	public TPEStack(PatternNode p, TPEStack parent){
		this.p = p;
		this.spar = parent;
		this.matches = new Stack<Match>();
		this.childStacks = new ArrayList<TPEStack>();
	}
	
	// gets the stacks for all descendants of p
	public List<TPEStack> getDescendantStacks() {
		//TODO - for each child get all descendants; = flatten childStacks
		List<TPEStack> descendants = new ArrayList<TPEStack>();
		//descendants.addAll(childStacks);
		for(TPEStack st:childStacks){
	//		System.out.println(st.getPatternNodeName());
		//	System.out.println("Size: " + st.getDescendantStacks().size());
			descendants.addAll(st.getDescendantStacks());
		}
		descendants.add(this);
		return descendants;
	}
	
	public List<TPEStack> getChildStacks(){
		return childStacks;
	}
	
	public void setParentTPEStack(TPEStack parent){
		this.spar = parent;
	}
	
	public PatternNode getPatternNode(){
		return p;
	}
	
	public TPEStack getTPEStack(){
		return spar;
	}
	
	public void push(Match m){ 
		matches.push(m); 
	}
	
	//first open element
	public Match top(){ 
		for(int i=matches.size()-1; i>=0; i--)
			if(matches.get(i).getStatus() == 1)
				return matches.get(i);
		return null;
	}
	
	public Match pop(){ 
		return matches.pop(); 
	}
	
	public Stack<Match> getMatches(){
		return matches;
	}
	
	public void removeMatch(Match m) {
		// TODO Auto-generated method stub
		matches.remove(m);
	}
	
	public String getPatternNodeName(){
		return p.getName();
	}
	
	public void populateTPEStacks(){
		//for each children, recursively create the TPEstacks for the descendants
		List<PatternNode> children = p.getChildren();
		//childStacks.add(this);
		if (children.size() == 0)
			return;
		for(PatternNode child: children){
			TPEStack stack = new TPEStack(child, this);
			stack.populateTPEStacks();
			childStacks.add(stack);
		}
	}

	public void addChildMatchToMatch(PatternNode child, Match m) {
		// TODO Auto-generated method stub
		top().addChildMatch(child, m);

	}
		
}
