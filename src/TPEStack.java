import java.util.Stack;


public class TPEStack {
	private	PatternNode p;
	private Stack <Match> matches;
	private	TPEStack spar;
	
	// gets the stacks for all descendants of p
	public Array <TPEStack> getDescendantStacks() {
		
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
