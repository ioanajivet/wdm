import java.util.Stack;

import org.xml.sax.*;
import org.xml.sax.helpers.LocatorImpl;

public class StackEval implements ContentHandler{

//		private TreePattern q;
		private TPEStack rootStack; // stack for the root of q
		// pre number of the last element which has started:
		private int currentPre = 0;
		// pre numbers for all elements having started but not ended yet:
		private Stack <Integer> preOfOpenNodes;
		
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			System.out.println("Opening tag : " + localName);
/*			for(s in rootStack.getDescendantStacks()){
				if(localName == s.p.name && s.spar.top().status == open){
					Match m = new Match(currentPre, s.spar.top(), s);
					// create a match satisfying the ancestor conditions
					// of query node s.p
					s.push(m); preOfOpenNodes.push(currentPre);
				}
				
				currentPre ++;
			}
				
			for (a in attributes.){
				// similarly look for query nodes possibly matched
				// by the attributes of the currently started element
				for (s in rootStack.getDescendantStacks()){
					if (a.name == s.p.name && s.par.top().status == open){
						Match ma = new Match(currentPre, s.spar.top(), s);
						s.push(ma);
					}
				}
				
				currentPre ++;
			}*/
		}

		@Override
		public void characters(char[] arg0, int arg1, int arg2)
				throws SAXException {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
			System.out.println("End the parsing of document");
			
		}
		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			System.out.println("Closing tag : " + localName);
			// we need to find out if the element ending now corresponded
						// to matches in some stacks
						// first, get the pre number of the element that ends now:
				//int preOflastOpen = preOfOpenNodes.pop();
						// now look for Match objects having this pre number:
		/*		for(s in rootStack.getDescendantStacks()){
					if (s.p.name == localName && s.top().status == open &&) s.top().pre == preOfLastOpen){
						// all descendants of this Match have been traversed by now.
						Match m = s.pop();
						// check if m has child matches for all children
						// of its pattern node
						for (pChild in s.p.getChildren()){
						// pChild is a child of the query node for which m was created
							if (m.children.get(pChild) == null){
						// m lacks a child Match for the pattern node pChild
						// we remove m from its Stack, detach it from its parent etc.
								remove(m, s);
							}
						}
						m.close();
					}
				}*/
			
		}
		
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
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub
			System.out.println("Start the parsing of document");
			
		}
		
		@Override
		public void startPrefixMapping(String arg0, String arg1)
				throws SAXException {
			// TODO Auto-generated method stub
			
		}
}
