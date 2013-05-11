import java.util.List;
import java.util.Stack;


public class TPEStack {
	private	PatternNode p;
	private Stack <Match> matches;
	private	TPEStack spar;
	
	// gets the stacks for all descendants of p
	public List<TPEStack> getDescendantStacks() {
		//TODO add implementation
		return null;
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
	
	public Match top(){ 
		return matches.firstElement(); 
	}
	
	public Match pop(){ 
		return matches.pop(); 
	}
		
}
