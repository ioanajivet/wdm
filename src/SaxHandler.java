// From an example found at http://smeric.developpez.com/java/cours/xml/sax/
import org.xml.sax.*;
import org.xml.sax.helpers.LocatorImpl;

public class SaxHandler implements ContentHandler 
{
    private Locator locator;

    /** Constructor */
   public SaxHandler() {
      super();
      // Set the default locator
      locator = new LocatorImpl();
   }

    /** Handler for the beginning and end of the document */
    public void startDocument() throws SAXException {
	System.out.println("Start the parsing of document");
    }

    public void endDocument() throws SAXException {
	System.out.println("End the parsing of document" );
    }
    
    /** Opening tag handler */
    public void startElement(String nameSpaceURI, 
			     String localName, 
			     String rawName, 
			     Attributes attributes) throws SAXException {
	System.out.println("Opening tag: " + localName);
	
	// Show the attributes, if any
	if (attributes.getLength() > 0) {
	    System.out.println("  Attributes:  ");
	    for (int index = 0; index < attributes.getLength(); index++) { 
	      System.out.println("     - " 
			       +  attributes.getLocalName(index) 
			       + " = " + attributes.getValue(index));
	    }
	}
    }

    /** Closing tag handler */
    public void endElement(String nameSpaceURI, 
			   String localName, String rawName) throws SAXException {
	System.out.print("Closing tag : " + localName);	
	System.out.println();
    }
    
    /** Character data handling */
    public void characters(char[] ch, int start, int end) throws SAXException {
	System.out.println("#PCDATA: " + new String(ch, start, end));
    }

    /** The following methods must be defined because
     they are declared in the abstract class      */
    public void setDocumentLocator(Locator value) {}
    public void ignorableWhitespace(char[] ch, 
				    int start, int end) throws SAXException {}
    public void processingInstruction(String target, 
					  String data) throws SAXException {}
    public void skippedEntity(String arg0) throws SAXException { }
    public void startPrefixMapping(String prefix, String URI) throws SAXException {
    }
    public void endPrefixMapping(String prefix) throws SAXException {
    }
}