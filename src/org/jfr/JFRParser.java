package org.jfr;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jfr.data.Feed;
import org.jfr.parser.AtomParser;
import org.jfr.parser.RssParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * The core of the parser.
 *
 * @author Ian
 */
public class JFRParser
{
	/**
	 * Parses the feed from a file.
	 * 
	 * @param file The file to parse.
	 * @return The Feed.
	 */
	public Feed parse(File file)
	{
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		
		try
		{
			DocumentBuilder builder = documentFactory.newDocumentBuilder();
			Document document = builder.parse(file);
			
			return parseDocument(document);
		}
		catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SAXException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Parses the document.
	 * 
	 * @param document The document to parse.
	 * @return The Feed.
	 */
	private Feed parseDocument(Document document)
	{
		document.getDocumentElement().normalize();
		
		Node element = document.getFirstChild();
		
		// Now that we have the first child, let's see if it is RSS or Atom.
		if (element.getNodeName().equalsIgnoreCase("rss"))
		{
			return (new RssParser()).parse(document);
		}
		else if (element.getNodeName().equalsIgnoreCase("atom"))
		{
			return (new AtomParser()).parse(document);
		}
		else
		{
			// TODO: A special Exception.
			throw new IllegalArgumentException("Yeah...");
		}
	}
}
