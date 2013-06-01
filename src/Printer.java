import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Printer {
	
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
	
	public void printFullTuplesNumbers(TPEStack rootStack) {
		List<String> results = new ArrayList<String>();
		
		System.out.println("\nFull resulting tuples: ");
		System.out.println(printFullHeader(rootStack.getPatternNode()));
		
		for(Match match: rootStack.getMatches()) 
			results.addAll(printFullTuples(match));
		
		for(String s: results)
			System.out.println(s);
		
	}
	
	public void printMarkedTuplesNumbers(TPEStack rootStack) {
		List<String> results = new ArrayList<String>();

		System.out.println("\nMarked resulting tuples: ");
		System.out.println(printHeader(rootStack.getPatternNode()));
		
		for(Match match: rootStack.getMatches())
			results.addAll(printMarkedTuples(match));
		
		for(String s: results)
			System.out.println(s);
		
		if (results.size() > 0)
			System.out.println("\nNumber of patterns found: " + results.size());
		else
			System.out.println("\nNo matching patterns found.");
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
	
	public void printTuplesTextInFile(PatternNode root, TPEStack rootStack, Map<Integer,String> texts) {
		Map<PatternNode, List<Match>> matchChildren;
		
		try {
			File file = new File("results/results_text.txt");

			if (!file.exists()) {
				file.createNewFile();
			}
 
			PrintWriter bw = new PrintWriter(new FileWriter(file.getAbsoluteFile()));
			bw.write(printHeader(root) + "\n");
			
			for(Match m: rootStack.getMatches()){
				if(m.getStack().getPatternNode().isMarked())
					if(texts.get(m.getPre()) == null)
						bw.write("----\t\t");
					else
						bw.write(texts.get(m.getPre()) + "\t\t");
				matchChildren = m.getChildren();
				List<PatternNode> patterns = root.getChildren();
				for(int i=0; i<patterns.size(); i++){
					List<Match> childMatches = matchChildren.get(patterns.get(i));
					for(Match mat: childMatches)
						if(mat.getStack().getPatternNode().isMarked())
							if(texts.get(mat.getPre()) == null)
								bw.write("----\t\t");
							else
								bw.write(texts.get(mat.getPre()) + "\t\t");
				}
				bw.write("\n");
			}
			
			bw.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void printTuplesNumbersInFile(PatternNode root, TPEStack rootStack, Map<Integer,String> texts) {
		Map<PatternNode, List<Match>> matchChildren;
		
		try {
			File file = new File("results/results_numbers.txt");
 
			if (!file.exists()) {
				file.createNewFile();
			}
 
			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			bw.write(printHeader(root) + "\n");
			
			for(Match m: rootStack.getMatches()){
				if(m.getStack().getPatternNode().isMarked())
					bw.write(m.getPre() + "\t\t\t");
				matchChildren = m.getChildren();
				List<PatternNode> patterns = root.getChildren();
				for(int i=0; i<patterns.size(); i++){
					List<Match> childMatches = matchChildren.get(patterns.get(i));
					for(Match mat: childMatches)
						if(mat.getStack().getPatternNode().isMarked())
							bw.write(mat.getPre() + "\t\t\t");
				}
				bw.write("\n");
			}
			
			bw.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		}	
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
}
