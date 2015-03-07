package org.jfr;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jfr.data.Feed;
import org.jfr.exception.ParserException;
import org.jfr.parser.AtomParser;
import org.jfr.parser.RssParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * The core of the parser.
 *
 * @author Ian
 */
public class JFRParser
{
	/**
	 * An instance of an RSS parser, if any.
	 */
	private RssParser rssParser;
	
	/**
	 * An instance of an Atom parser, if any.
	 */
	private AtomParser atomParser;
	
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
		catch (Exception e)
		{
			throw new ParserException("An error occurred while attempting to parse the file.", e);
		}
	}
	
	/**
	 * Parses the document, passing it on to the appropriate feed type parser.
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
			return getRssParser().parse(document);
		}
		else if (element.getNodeName().equalsIgnoreCase("feed"))
		{
			return getAtomParser().parse(document);
		}
		else
		{
			throw new ParserException("The parser could not determine the feed type from the file.");
		}
	}
	
	/**
	 * Returns an instance of an RSS parser.
	 * 
	 * @return RssParser.
	 */
	private RssParser getRssParser()
	{
		if (rssParser != null)
		{
			return rssParser;
		}
		
		rssParser = new RssParser();
		return rssParser;
	}
	
	/**
	 * Returns an instance of an Atom parser.
	 * 
	 * @return AtomParser.
	 */
	private AtomParser getAtomParser()
	{
		if (atomParser != null)
		{
			return atomParser;
		}
		
		atomParser = new AtomParser();
		return atomParser;
	}
}
