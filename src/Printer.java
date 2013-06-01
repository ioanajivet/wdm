import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Printer {
	
	Map<Integer, String> textNodes;
	
	public Printer(Map<Integer, String> text){
		this.textNodes = text;
	}
	
	private List<String> printFullTuples(Match current){
		String currentText = current.getPre() + "\t";
		List<String> childStrings = new ArrayList<String>();
		List<String> finalStrings = new ArrayList<String>();
		
		//initialize the list that will be returned with the value of the current match
		childStrings.add(currentText);	
		
		Map<PatternNode, List<Match>> matchChildren = current.getChildren();			//children matches of the current match		
		List<PatternNode> childPatternNodes = current.getPatternNode().getChildren();	//children nodes of the node of the match
		
		for(PatternNode childPattern: childPatternNodes){
			List<Match> patternChildren = matchChildren.get(childPattern);
			if(patternChildren != null)
				for(Match m : patternChildren){
					List<String> currentChildStrings = printFullTuples(m);
					for(String s: currentChildStrings){
						for(String parent: childStrings)
							finalStrings.add(parent + s);
					}
				}
			else {
				String nulls = generateNullChildren(childPattern);
				for(String parent: childStrings)
					finalStrings.add(parent + nulls);
			}
			childStrings.clear();
			childStrings.addAll(finalStrings);
			finalStrings.clear();
		}

		return childStrings;
	}

	private List<String> printMarkedTuples(Match current){
		String currentText = "";
		if(current.getPatternNode().isMarked())
			currentText = current.getPre() + "\t";
		List<String> childStrings = new ArrayList<String>();
		List<String> finalStrings = new ArrayList<String>();
		
		//initialize the list that will be returned with the value of the current match
		childStrings.add(currentText);	
		
		Map<PatternNode, List<Match>> matchChildren = current.getChildren();			//children matches of the current match		
		List<PatternNode> childPatternNodes = current.getPatternNode().getChildren();	//children nodes of the node of the match
		
		for(PatternNode childPattern: childPatternNodes){
			List<Match> patternChildren = matchChildren.get(childPattern);
			if(patternChildren != null)
				for(Match m : patternChildren){
					List<String> currentChildStrings = printMarkedTuples(m);
						for(String s: currentChildStrings){
							for(String parent: childStrings)
								finalStrings.add(parent + s);
						}
				}
			else {
				String nulls = generateMarkedNullChildren(childPattern);
				for(String parent: childStrings)
					finalStrings.add(parent + nulls);
			}
			
			childStrings.clear();
			childStrings.addAll(finalStrings);
			finalStrings.clear();
		}

		return childStrings;
	}
	
	public void printFullTuplesNumbers(TPEStack rootStack, PrintStream ps) {
		List<String> results = new ArrayList<String>();
		
		ps.println("\nFull resulting tuples: ");
		ps.println(printFullHeader(rootStack.getPatternNode()));
		
		for(Match match: rootStack.getMatches()) 
			results.addAll(printFullTuples(match));
		
		for(String s: results)
			ps.println(s);
		
	}
	
	public void printMarkedTuplesNumbers(TPEStack rootStack, PrintStream ps) {
		List<String> results = new ArrayList<String>();

		ps.println("\nMarked resulting tuples: ");
		ps.println(printHeader(rootStack.getPatternNode()));
		
		for(Match match: rootStack.getMatches())
			results.addAll(printMarkedTuples(match));
		
		for(String s: results)
			ps.println(s);
		
		if (results.size() > 0)
			ps.println("\nNumber of patterns found: " + results.size());
		else
			ps.println("\nNo matching patterns found.");
	}
	
	private String generateNullChildren(PatternNode node) {
		String nulls = "null\t";
		for(PatternNode child: node.getChildren())
			nulls += generateNullChildren(child);
		return nulls;
	}

	private String generateMarkedNullChildren(PatternNode node){
		String nulls;
		if(node.isMarked())
			nulls = "null\t";
		else
			nulls = "";
		for(PatternNode child: node.getChildren())
			nulls += generateMarkedNullChildren(child);
		return nulls;
	}

	private String printHeader(PatternNode node){
		String header = "";
		if(node.isMarked())
			header += node.getName() + "\t";
		for(PatternNode child: node.getChildren())
			header += printHeader(child);
		return header;
	}
	
	private String printFullHeader(PatternNode node){
		String header = node.getName() + "\t";
		for(PatternNode child: node.getChildren())
			header += printFullHeader(child);
		return header;
	}

	private List<String> printMarkedSubtree(Match current){
		String openTag = "";
		String endTag = "";
		String textInNode = textNodes.get(current.getPre());
		if(current.getPatternNode().isMarked()){
			openTag = "<" + current.getPatternNode().getName() + ">";
			if(textInNode != null)
				openTag += textInNode;
			endTag = "</" + current.getPatternNode().getName() + "> ";
		}
		
		
		List<String> childStrings = new ArrayList<String>();
		List<String> finalStrings = new ArrayList<String>();
		
		//initialize the list that will be returned with the value of the current match
		childStrings.add(openTag);	
		
		Map<PatternNode, List<Match>> matchChildren = current.getChildren();			//children matches of the current match		
		List<PatternNode> childPatternNodes = current.getPatternNode().getChildren();	//children nodes of the node of the match
		
		for(PatternNode childPattern: childPatternNodes){
			List<Match> patternChildren = matchChildren.get(childPattern);
			if(patternChildren != null)
				for(Match m : patternChildren){
					List<String> currentChildStrings = printMarkedSubtree(m);
						for(String s: currentChildStrings){
							for(String parent: childStrings)
								finalStrings.add(parent + s);
						}
				}
			else {
				String nulls = generateMarkedNullChildren(childPattern);
				for(String parent: childStrings)
					finalStrings.add(parent + nulls);
			}
			
			childStrings.clear();
			childStrings.addAll(finalStrings);
			finalStrings.clear();
		}

		for(String s: childStrings)
			finalStrings.add(s + endTag);
		
		return finalStrings;
	}
	
	public void printMarkedSubtrees(TPEStack rootStack, PrintStream ps) {
		List<String> results = new ArrayList<String>();

		ps.println("\nMarked resulting sub-trees: ");
		ps.println(printHeader(rootStack.getPatternNode()));
		
		for(Match match: rootStack.getMatches())
			results.addAll(printMarkedSubtree(match));
		
		for(String s: results)
			ps.println(s);
		
		if (results.size() > 0)
			ps.println("\nNumber of patterns found: " + results.size());
		else
			ps.println("\nNo matching patterns found.");
	}
	
	//TODO print in files
		public void printTuplesInFile(PatternNode root, TPEStack rootStack) {
			
			try {
				File file = new File("results/results_numbers.txt");
	 
				if (!file.exists()) {
					file.createNewFile();
				}
	 
				PrintStream ps = new PrintStream(file);
				printFullTuplesNumbers(rootStack, ps);
	 
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}

		
}
