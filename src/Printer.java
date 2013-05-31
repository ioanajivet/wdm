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
	
	public void printTuplesNumbers(PatternNode root, TPEStack rootStack, Map<Integer,String> texts) {
		// TODO clean-up
		Map<PatternNode, List<Match>> matchChildren;
		
		System.out.println(printHeader(root));
		
		for(Match m: rootStack.getMatches()){
			if(m.getStack().getPatternNode().isMarked())
				System.out.print(m.getPre() + "\t\t");
			matchChildren = m.getChildren();
			List<PatternNode> patterns = root.getChildren();
			for(int i=0; i<patterns.size(); i++){
				List<Match> childMatches = matchChildren.get(patterns.get(i));
				for(Match mat: childMatches)
					if(mat.getStack().getPatternNode().isMarked())
						System.out.print(mat.getPre() + "\t\t");
			}
			System.out.println();
		}
		
	}
	
	public List<String> printTuples(String previousMatches, Match current){
		String currentText = current.getPre() + " ";
		List<String> childStrings = new ArrayList<String>();
		List<String> finalStrings = new ArrayList<String>();
		
		//initialize the list that will be returned with the value of the current match
		childStrings.add(currentText);	
		
		//get the child matches of the current Match, extract the PatternNodes 
				//and order them so they are printed according to the header = assure that the Iterator doesn't go random
		Map<PatternNode, List<Match>> matchChildren = current.getChildren();		
		List<PatternNode> reorderedPatternKeySet = reorder(matchChildren.keySet());
		
		for(PatternNode childPattern: reorderedPatternKeySet){
			List<Match> patternChildren = matchChildren.get(childPattern);
			for(Match m : patternChildren){
				List<String> currentChildStrings = printTuples(currentText, m);
				for(String s: currentChildStrings){
					for(String parent: childStrings)
						finalStrings.add(parent + s);
				}
			}
			childStrings.clear();
			childStrings.addAll(finalStrings);
			finalStrings.clear();
		}

		return childStrings;
	}

	private List<PatternNode> reorder(Set<PatternNode> keySet) {
		// TODO reorder them according to the header - the header must be added here too
		List<PatternNode> listOfNodes = new ArrayList<PatternNode>();
		listOfNodes.addAll(keySet);
		return listOfNodes;
	}

	public void printTuplesText(PatternNode root, TPEStack rootStack, Map<Integer,String> texts) {
		Map<PatternNode, List<Match>> matchChildren;
		System.out.println(printHeader(root));
		
		for(Match m: rootStack.getMatches()){
			if(m.getStack().getPatternNode().isMarked())
				System.out.print(texts.get(m.getPre()) + "\t\t");
			matchChildren = m.getChildren();
			List<PatternNode> patterns = root.getChildren();
			for(int i=0; i<patterns.size(); i++){
				List<Match> childMatches = matchChildren.get(patterns.get(i));
				for(Match mat: childMatches)
					if(mat.getStack().getPatternNode().isMarked())
						System.out.print(texts.get(mat.getPre()) + "\t\t");
			}
			System.out.println();
		}
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
			header += node.getName() + "\t\t";
		for(PatternNode child: node.getChildren())
			header += printHeader(child);
		return header;
	}
}
