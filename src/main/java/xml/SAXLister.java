import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import org.apache.xerces.parsers.SAXParser;

/** Simple lister - extract name and email tags from a user file.
 * Updated for SAX 2.0
 * @author Ian Darwin
 * @version $Id$
 */
public class SaxLister {

	class PeopleHandler extends DefaultHandler {

		boolean name = false;
		boolean mail = false;

		public void startElement(String nsURI, String strippedName,
			String tagName, Attributes attributes)
		throws SAXException {
			if (tagName.equalsIgnoreCase("name"))
				name = true;
			if (tagName.equalsIgnoreCase("email"))
				mail = true;
		}

		public void characters(char[] ch, int start, int length) {
			if (name) {
				System.out.println("Name:  " + new String(ch, start, length));
				name = false;
			} else if (mail) {
				System.out.println("Email: " + new String(ch, start, length));
				mail = false;
			}
		}
	}

	public void list() throws Exception {
		XMLReader parser = XMLReaderFactory.createXMLReader(
			"org.apache.xerces.parsers.SAXParser");	// should load properties
		parser.setContentHandler(new PeopleHandler());
		parser.parse("people.xml");
	}

	public static void main(String[] args) throws Exception { 
		new SaxLister().list();
	}
}