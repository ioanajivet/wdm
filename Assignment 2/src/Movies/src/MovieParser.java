import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MovieParser extends DefaultHandler{

    String content;
    String tmpValue;
    String roleActor;
    MovieSet movieTmp;
    Artist dirTmp;
    Artist actorTmp;
    Stack<String> chckParent;

    
    public MovieParser(String content) {
        this.content = content;
        chckParent = new Stack<String>();
     
    }
    public MovieSet parseDocument() {
        // parse
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(new StringBufferInputStream(content), this);
            
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
		return movieTmp;
    }
 
    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {
        // clear tmpValue on start of element
        tmpValue=null;
        
        if (elementName.equalsIgnoreCase("movie")) {
        	movieTmp = new MovieSet();
        }
        if (elementName.equalsIgnoreCase("director")) {
        	chckParent.push("director");
        	dirTmp = new Artist();
        }
        if (elementName.equalsIgnoreCase("actor")) {
        	chckParent.push("actor");
        	actorTmp = new Artist();
        }

    }
    @Override
    public void endElement(String s, String s1, String element) throws SAXException {

        if (element.equalsIgnoreCase("title")) {
        	movieTmp.setTitle(tmpValue);
        }
        if (element.equalsIgnoreCase("year")) {
        	movieTmp.setYear(tmpValue);
        }
        if(element.equalsIgnoreCase("country")){
        	movieTmp.setCountry(tmpValue);
        }
        if(element.equalsIgnoreCase("genre")){
        	movieTmp.setGenre(tmpValue);
        }
        if(element.equalsIgnoreCase("role")){
        	
        	roleActor = tmpValue;
        	movieTmp.setRole(tmpValue);
        }
        if(element.equalsIgnoreCase("first_name")){
        	if (chckParent.peek().equals("director")){
        		dirTmp.setFirstName(tmpValue);
        	}
        	if (chckParent.peek().equals("actor")){
        		actorTmp.setFirstName(tmpValue);
        	}
        }
        if(element.equalsIgnoreCase("last_name")){
        	if (chckParent.peek().equals("director")){
        		dirTmp.setLastName(tmpValue);
        	}
        	if (chckParent.peek().equals("actor")){
        		actorTmp.setLastName(tmpValue);
        	}
        }
        if(element.equalsIgnoreCase("birth_date")){
        	if (chckParent.peek().equals("director")){
        		dirTmp.setBirthDate(tmpValue);
        	}
        	if (chckParent.peek().equals("actor")){
        		actorTmp.setBirthDate(tmpValue);
        	}
        }
        if(element.equalsIgnoreCase("director")){
        	movieTmp.setDirector(dirTmp);
        	if(chckParent.peek().equals("director"))
        		chckParent.pop();
        }
        if(element.equalsIgnoreCase("actor")){
        	Map<String,Artist> tmp = movieTmp.getActors();
        	tmp.put(roleActor, actorTmp);
        	if(chckParent.peek().equals("actor"))
        		chckParent.pop();
        }
    }
    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {
        tmpValue = new String(ac, i, j);
    }

}

