import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.xml.sax.*;

public class BasicStackEval implements ContentHandler{

		private PatternNode root;												// the pattern tree
		private TPEStack rootStack; 											// stack for the root of the tree
		private int currentPre = 1;												// counter for the current element in the XML file
		private Stack <Integer> preOfOpenNodes = new Stack<Integer>();			// stack with the preNumber for all elements opened but not closed yet					
		private Map<Integer,String> texts = new HashMap<Integer,String>();		// list to collect text 
		private Printer printer = new Printer();
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			System.out.println("Opening tag : " + localName);
			Match m;
			List<TPEStack> descendantStacks = rootStack.getDescendantStacks(); 
			for(TPEStack s : descendantStacks){
				if(localName.equals(s.getPatternNode().getName())
						&& (s.getTPEStack() == null || (s.getTPEStack().top() != null && s.getTPEStack().top().getStatus() == 1))){

					if(s.getTPEStack() == null)
						m = new Match(currentPre, null, s);
					else 
						m = new Match(currentPre, s.getTPEStack().top(), s);
					
					// create a match satisfying the ancestor conditions of query node s.patternNode
					s.push(m); 
					if(s.getTPEStack() != null)
						s.getTPEStack().addChildMatchToMatch(s.getPatternNode(),m);
				}
			}
			preOfOpenNodes.push(currentPre);
			currentPre ++;
			
			for(int i=0; i<attributes.getLength(); i++){
				String atrName = "@" + attributes.getLocalName(i);
				String atrValue = attributes.getValue(i);
				// similarly look for query nodes possibly matched
				// by the attributes of the currently started element
			
				for (TPEStack s : descendantStacks){
					if (atrName.equals(s.getPatternNode().getName()) && (s.getTPEStack() == null 
							|| s.getTPEStack().top().getStatus() == 1)){
						if(s.getTPEStack() == null)			//this is the root node
							m = new Match(currentPre, null, s);				
						else 								//this is a descendant in the tree pattern
							m = new Match(currentPre, s.getTPEStack().top(), s);

						//m.addText(atrValue);
						texts.put(currentPre, atrValue);
						m.close();
						s.push(m); 
						if(s.getTPEStack() != null)
							s.getTPEStack().addChildMatchToMatch(s.getPatternNode(),m);
						break;
					}	
				}
				currentPre ++;
			}
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			String text = new String(ch, start, length).trim();
			int lastOpened = preOfOpenNodes.lastElement();
			if(!text.equals(""))
				if(texts.containsKey(lastOpened))
					texts.put(lastOpened, texts.get(lastOpened) + " " + text);
				else
					texts.put(lastOpened, text);
		}
		
		@Override
		public void endDocument() throws SAXException {
			// TODO remove tested stuff and call for print tuples.
			System.out.println("End the parsing of document");
			
			//printer.printTuplesNumbersInFile(root, rootStack, texts);
			//printer.printTuplesTextInFile(root, rootStack, texts);
			
			System.out.println("Results: ");
			List<String> results = printer.printTuples("", rootStack.getMatches().peek());
			for(String s: results)
				System.out.println(s);
			System.out.println("\nNumber of patterns found: " + results.size());
			
		}
			
		
		
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			System.out.println("Closing tag : " + localName);
			// we need to find out if the element ending now corresponded
						// to matches in some stacks
			Match m;
			List<TPEStack> descendantStacks = rootStack.getDescendantStacks(); 
						// first, get the pre number of the element that ends now:
				int preOfLastOpen = preOfOpenNodes.pop();
						// now look for Match objects having this pre number:
				
				for(TPEStack s : descendantStacks){
					if (localName.equals(s.getPatternNode().getName())
						&& (s.top() == null || s.top().getStatus() == 1)
							&& (s.top() == null || s.top().getPre() == preOfLastOpen)){
						// all descendants of this Match have been traversed by now.
						if(s.top() == null)
							continue;						
						
						m = s.top();
						s.top().close();
						// check if m has child matches for all children of its pattern node
						List<PatternNode> children = s.getPatternNode().getChildren(); 
						
						for (PatternNode pChild: children){
						// pChild is a child of the query node for which m was created
							Map<PatternNode, List<Match>> matchChildren = m.getChildren();
							if (matchChildren.get(pChild) == null || matchChildren.get(pChild).size() == 0){		
								//if m lacks a child Match for the pattern node pChild
								// we remove m from its Stack, detach it from its parent etc.
								s.removeMatch(m);
								if(m.getParentMatch() != null)
									m.getParentMatch().removeChildMatch(s.getPatternNode(),m);
								break;
							}
						}
					break;
					}
					
				}
			
		}

		@Override
		public void startDocument() throws SAXException {
			System.out.println("Start the parsing of document");
		}
		
		public void readTreePattern() {
			//TODO implemented in a proper manner -> read from file or something
			root = new PatternNode("people");
			PatternNode person = new PatternNode("person");
			PatternNode name = new PatternNode("name");
			PatternNode first = new PatternNode("first");
			PatternNode last = new PatternNode("last");
			name.addChild(first);
			name.addChild(last);
			last.mark();
			PatternNode email = new PatternNode("email");
			email.mark();
			person.addChild(email);
			person.addChild(name);
			root.addChild(person);
	
			initializeStack(root);
		}

		private void initializeStack(PatternNode root) {
			if(root == null)
				return;
			rootStack = new TPEStack(root,  null);
			rootStack.populateTPEStacks();
			
			System.out.println("--- Initialize complete");

		}
		
		
		//unused methods from the interface ContentHandler
		@Override
		public void endPrefixMapping(String arg0) throws SAXException {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
				throws SAXException {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void processingInstruction(String arg0, String arg1)
				throws SAXException {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void setDocumentLocator(Locator arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void skippedEntity(String arg0) throws SAXException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void startPrefixMapping(String arg0, String arg1)
				throws SAXException {
			// TODO Auto-generated method stub
			
		}

}

