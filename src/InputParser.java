import java.util.Stack;

import org.xml.sax.*;

public class InputParser implements ContentHandler{


		private Stack<PatternNode> treeStack;
		
		public InputParser(){
			this.treeStack = new Stack<PatternNode>();
		}
		
		@Override
		public void startDocument() throws SAXException {
			System.out.println("Start the parsing of document");
		}
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			
			System.out.println("Opening tag : " + localName);
			boolean marked = false;
			boolean optional = false;
			String wildcard = null;
			
			for(int i=0; i<attributes.getLength(); i++){
				String atrName = "@" + attributes.getLocalName(i);
				String atrValue = attributes.getValue(i);
				if (atrName.equals("@marked") && (atrValue.equals("true") || atrValue.equals("t"))){
					marked = true;
				}
				if (atrName.equals("@optional") && (atrValue.equals("true") || atrValue.equals("t"))){
					optional = true;
				}
				if (atrName.equals("@wc")){
					wildcard = atrValue;
				}
			}
			PatternNode node = new PatternNode(localName, marked, optional, wildcard);
			treeStack.add(node);		// Add node to the pattern tree stack
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			String text = new String(ch, start, length).trim();
			if (!text.equals("")){
				PatternNode topNode = treeStack.peek();
				if(topNode.getText() == null)
					topNode.setText(text);
				else
					topNode.setText(topNode.getText() + " " + text);
			}
		}
		
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			System.out.println("Closing tag : " + localName);
			// only do the following for all nodes except root
			if (treeStack.size() > 1){
				PatternNode removedNode = treeStack.pop();
				PatternNode topNode = treeStack.peek();
				topNode.addChild(removedNode);	
			}

		}
		
		@Override
		public void endDocument() throws SAXException {
			
			System.out.println("End the parsing of input tree pattern xml");
			BasicStackEval eval = new BasicStackEval();
			// Pop the last element(root) and initialize the stack
			eval.initializeStack(treeStack.pop());
	
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

