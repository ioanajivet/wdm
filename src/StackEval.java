import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.xml.sax.*;

public class StackEval implements ContentHandler{

		private static TPEStack rootStack; 										// stack for the root of the tree
		private int currentPre = 1;												// counter for the current element in the XML file
		private Stack <Integer> preOfOpenNodes = new Stack<Integer>();			// stack with the preNumber for all elements opened but not closed yet					
		private Map<Integer,String> texts = new HashMap<Integer,String>();		// list to collect text 
		private Printer printer = new Printer(texts);
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			System.out.println("Opening tag : " + localName);
			Match m;
			
			//search the list of stacks to find if we have a potential match
			List<TPEStack> descendantStacks = rootStack.getDescendantStacks(); 
			for(TPEStack s : descendantStacks){
				if((localName.equals(s.getPatternNodeName()) || s.getPatternNodeName().equals("wildcard"))
						&& (s.getTPEStack() == null || (s.getTPEStack().top() != null && s.getTPEStack().top().getStatus() == 1))){
					
					//if the parent stack is null, it means we have a match for the root node
					if(s.getTPEStack() == null)
						m = new Match(currentPre, null, s);
					else 
						m = new Match(currentPre, s.getTPEStack().top(), s);
					
					// create a match satisfying the ancestor conditions of query node s.patternNode
					s.push(m); 
					if(s.getTPEStack() != null)
						s.getTPEStack().addChildMatchToMatch(s.getPatternNode(),m);			//insert the match as a child in the match for the parent tag
					break;
				}
			}
			preOfOpenNodes.push(currentPre);
			currentPre ++;
			
			//analyze the attributes in the same manner.
			for(int i=0; i<attributes.getLength(); i++){
				String atrName = "@" + attributes.getLocalName(i);
				String atrValue = attributes.getValue(i);
			
				for (TPEStack s : descendantStacks){
					if (atrName.equals(s.getPatternNode().getName()) && (s.getTPEStack() == null 
							|| s.getTPEStack().top().getStatus() == 1)){
						if(s.getTPEStack() == null)			//this is the root node
							m = new Match(currentPre, null, s);				
						else 								//this is a descendant in the tree pattern
							m = new Match(currentPre, s.getTPEStack().top(), s);

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
			System.out.println("End the parsing of document");
			
			printer.printFullTuplesNumbers(rootStack, System.out);
			printer.printMarkedTuplesNumbers(rootStack, System.out);
			printer.printMarkedSubtrees(rootStack, System.out);
		}
			
		
		
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			System.out.println("Closing tag : " + localName);
			// we need to find out if the element ending now corresponded
						// to matches in some stacks
			Match m;
			List<TPEStack> descendantStacks = rootStack.getDescendantStacks(); 
			int preOfLastOpen = preOfOpenNodes.pop();
	
			//look for matches that are associated with the number of the current ending tag
			for(TPEStack s : descendantStacks){
				if ((localName.equals(s.getPatternNode().getName()) || s.getPatternNodeName().equals("wildcard"))
						&& (s.top() == null || s.top().getStatus() == 1)
						&& (s.top() == null || s.top().getPre() == preOfLastOpen)){
					
					// all descendants of this Match have been traversed by now.
					if(s.top() == null)
						continue;						

					//close the match to remove it from the analyzed set of matches
					m = s.top();
					s.top().close();

					//check for value predicate
					String valuePredicate = m.getPatternNode().getText();
					if(valuePredicate != null && !valuePredicate.equals(texts.get(m.getPre()))){
						s.removeMatch(m);
						if(m.getParentMatch() != null)
							m.getParentMatch().removeChildMatch(s.getPatternNode(),m);
						break;
					}

					// check if m has child matches for all children of its pattern node; if not, remove it from the stack and detach it from the parent match
					List<PatternNode> children = s.getPatternNode().getChildren(); 
					for (PatternNode pChild: children){
						Map<PatternNode, List<Match>> matchChildren = m.getChildren();
						if (!pChild.isOptional() &&											//check if the child is optional
								(matchChildren.get(pChild) == null || matchChildren.get(pChild).size() == 0)){		
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

		//initialize the TPEStacks
		public void initializeStack(PatternNode root) {
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

