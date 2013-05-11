import java.io.IOException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/** Example of a very basic document analysis with SAX. */

public class SaxExample {

    /** Constructor */
    public SaxExample(String uri) throws SAXException, IOException {
	XMLReader saxReader = 
	    XMLReaderFactory.createXMLReader(
              "org.apache.xerces.parsers.SAXParser");
	//saxReader.setContentHandler(new SaxHandler());
	saxReader.setContentHandler(new StackEval());
	saxReader.parse(uri);
    }
    
    public static void main(String[] args) {
	if (0 == args.length || 1 < args.length) {
	    System.out.println("Usage : SaxExample uri ");
	    System.exit(1);
	}
		
	try {
	    SaxExample parser = new SaxExample(args[0]);
	} catch (Throwable t) {
	    t.printStackTrace();
	}
    }
}