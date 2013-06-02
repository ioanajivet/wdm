import java.io.IOException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class Main {
    
    public Main(String uriPattern, String uri) throws SAXException, IOException {
	XMLReader saxReader1 = 
	    XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
	XMLReader saxReader2 = 
		XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
	
	InputParser patternParser = new InputParser();
	saxReader1.setContentHandler(patternParser);
	saxReader1.parse(uriPattern);
	
	StackEval eval = new StackEval();
	saxReader2.setContentHandler(eval);
	saxReader2.parse(uri);

    }
    
    public static void main(String[] args) {
	if (0 == args.length || 2 < args.length) {
	    System.out.println("Usage : [pattern-tree uri] [XML file uri] ");
	    System.exit(1);
	}
		
	try {
	    Main parser = new Main(args[0], args[1]);
	} catch (Throwable t) {
	    t.printStackTrace();
	}
    }
}