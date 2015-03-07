package org.jfr.parser;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jfr.data.Author;
import org.jfr.data.AuthorType;
import org.jfr.data.Feed;
import org.jfr.data.FeedItem;
import org.jfr.exception.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Implements the parser for Atom feeds.
 *
 * @author Ian
 */
public class AtomParser implements FeedParserIF
{
	/**
	 * Parses the Atom feed.
	 * 
	 * @param document
	 */
	@Override
	public Feed parse(Document document)
	{
		if (document.getFirstChild() == null)
		{
			throw new ParserException("There was nothing found within the Atom feed source.");
		}
		
		// Get the child nodes.
		NodeList childNodes = document.getFirstChild().getChildNodes();
		
		// Handle each node. We will need to pass the feed to be built upon.
		Feed feed = new Feed();
		
		for (int index = 0; index < childNodes.getLength(); index++)
		{
			Node node = childNodes.item(index);
			
			// Pass text nodes.
			if (node.getNodeType() == Node.TEXT_NODE)
			{
				continue;
			}
			
			parseNode(feed, node);
		}

		return feed;
	}
	
	/**
	 * Parses the root level node within the feed.
	 * 
	 * @param feed
	 * @param node
	 */
	private void parseNode(Feed feed, Node node)
	{
		switch (node.getNodeName().toLowerCase())
		{
			case "title":
				feed.setTitle(getNodeValue(node));
				break;
				
			case "author":
			case "contributor":
				feed.addAuthor(getAuthor(node));
				break;
			
			case "updated":
				feed.setUpdated(getNodeValue(node));
				break;
				
			case "link":
				// Make sure it isn't some other link.
				if (getNodeAttribute(node, "rel") != null && !getNodeAttribute(node, "rel").equalsIgnoreCase("alternate"))
				{
					break;
				}
				
				feed.setLink(getNodeAttribute(node, "href"));
				break;
			
			case "subtitle":
				feed.setDescription(getNodeValue(node));
				break;
			
			case "entry":
				parseEntry(feed, node);
				break;
		}
	}
	
	/**
	 * Returns the text value of the node. This will also take into consideration the type
	 * attribute for elements that it is applicable as well.
	 * 
	 * @param node
	 * @return
	 */
	private String getNodeValue(Node node)
	{
		return getNodeValue(node, false);
	}
	
	/**
	 * Returns the text value of the node. This will also take into consideration the type
	 * attribute for elements that it is applicable as well.
	 * 
	 * @param node
	 * @param allowXhtml
	 * @return
	 */
	private String getNodeValue(Node node, boolean allowXhtml)
	{
		NamedNodeMap attr = node.getAttributes();
		
		// If there is no type attribute or if the type attribute starts with text or is html,
		// there isn't anything we need to do.
		if (attr.getNamedItem("type") == null || attr.getNamedItem("type").getNodeValue().toLowerCase().startsWith("text")
				|| !allowXhtml || attr.getNamedItem("type").getNodeValue().equalsIgnoreCase("html"))
		{
			Node textNode = node.getChildNodes().item(0);
			
			return textNode != null ? textNode.getNodeValue() : "";
		}
		
		// It should be XHTML or end in +xml or /xml.
		String type = attr.getNamedItem("type").getNodeValue();
		if (!type.equalsIgnoreCase("xhtml") && !type.toLowerCase().endsWith("+xml") && !type.toLowerCase().endsWith("/xml"))
		{
			throw new ParserException("The parser could not handle a tag (" + node.getNodeName() + ") with a type of: " + type);
		}
		
		return StringEscapeUtils.escapeHtml4(convertNodeToString(node));
	}
	
	/**
	 * Returns the value of the attribute on the element.
	 * 
	 * @param node
	 * @param attrName
	 * @return Returns the value of the attribute or null if it wasn't defined.
	 */
	private String getNodeAttribute(Node node, String attrName)
	{
		NamedNodeMap attrMap = node.getAttributes();
		Node attrNode = attrMap.getNamedItem(attrName);
		
		return attrNode != null ? attrNode.getNodeValue() : null;
	}
	
	/**
	 * Parses an entry element.
	 * 
	 * @param feed
	 * @param node
	 */
	private void parseEntry(Feed feed, Node node)
	{
		NodeList childNodes = node.getChildNodes();
		
		FeedItem feedItem = new FeedItem();
		for (int index = 0; index < childNodes.getLength(); index++)
		{
			Node childNode = childNodes.item(index);
			
			// We aren't interested in text nodes.
			if (childNode.getNodeType() == Node.TEXT_NODE)
			{
				continue;
			}
			
			parseEntryNode(feedItem, childNode);
		}
		
		// If there was no author at the <entry> level, it will inherit from the root.
		if (feedItem.getAuthors() == null || feedItem.getAuthors().size() == 0)
		{
			feedItem.setAuthors(feed.getAuthors());
		}
		
		feed.getItems().add(feedItem);
	}
	
	/*
	 * title, link href, updated, author (multiple name),
	 * id (atom base).
	 * 
	 * entry: title, link href, id, updated, summary, content
	 */
	
	private void parseEntryNode(FeedItem feedItem, Node node)
	{
		switch (node.getNodeName().toLowerCase())
		{
			case "title":
				feedItem.setTitle(getNodeValue(node, true));
				break;
				
			case "link":
				// Make sure it isn't some other link.
				if (getNodeAttribute(node, "rel") != null && !getNodeAttribute(node, "rel").equalsIgnoreCase("alternate"))
				{
					break;
				}
				
				feedItem.setLink(getNodeAttribute(node, "href"));
				break;
				
			case "id":
				feedItem.setGuid(getNodeValue(node));
				break;
				
			case "updated":
				feedItem.setUpdated(getNodeValue(node));
				break;
				
			case "published":
				feedItem.setPublished(getNodeValue(node));
				break;
				
			// TODO This is bad... possibly overwriting one or the other.
			case "summary":
				feedItem.setDescription(getNodeValue(node, true));
				break;
				
			case "content":
				feedItem.setDescription(getNodeValue(node, true));
				break;
				
			case "author":
			case "contributor":
				feedItem.addAuthor(getAuthor(node));
				break;
		}
	}
	
	/**
	 * Returns an Author instance representing the node.
	 * 
	 * @param node
	 * @return Author
	 */
	private Author getAuthor(Node node)
	{
		NodeList childNodes = node.getChildNodes();
		
		Author author = new Author();
		author.setType(node.getNodeName().equalsIgnoreCase("contributor") ? AuthorType.Contributor : AuthorType.Author);
		
		for (int index = 0; index < childNodes.getLength(); index++)
		{
			Node childNode = childNodes.item(index);
			
			setAuthorAttribute(author, childNode);
		}
		
		return author;
	}
	
	/**
	 * Sets the appropriate attribute on the Author instance.
	 * 
	 * @param author
	 * @param node
	 */
	private void setAuthorAttribute(Author author, Node node)
	{
		switch (node.getNodeName().toLowerCase())
		{
			case "name":
				author.setName(getNodeValue(node));
				break;
				
			case "email":
				author.setEmail(getNodeValue(node));
				break;
				
			case "uri":
				author.setUrl(getNodeValue(node));
				break;
		}
	}
	
	/**
	 * Converts the contents of a node to a string (this excludes the node itself).
	 * 
	 * @param node
	 * @param isXhtml
	 * @return
	 */
	private String convertNodeToString(Node node)
	{
		// We don't want to include the node itself.
		NodeList childNodes = node.getChildNodes();
		
		StringWriter writer = new StringWriter();
		
		try
		{
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			
			for (int index = 0; index < childNodes.getLength(); index++)
			{
				Node childNode = childNodes.item(index);
				
				transformer.transform(new DOMSource(childNode), new StreamResult(writer));
			}
			
			return writer.toString().trim();
		}
		catch (Exception e)
		{
			throw new ParserException("An error occurred while converting an XML node to a string.", e);
		}
	}
}
