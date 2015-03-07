package org.jfr.parser;

import org.jfr.data.Feed;
import org.jfr.data.FeedItem;
import org.jfr.parser.FeedParserIF;
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
	 */
	@Override
	public Feed parse(Document document)
	{
		if (document.getFirstChild() == null)
		{
			// TODO: Exception
			throw new IllegalArgumentException();
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
		
		// TODO Auto-generated method stub
		return feed;
	}
	
	private void parseNode(Feed feed, Node node)
	{
		switch(node.getNodeName().toLowerCase())
		{
			case "title":
				feed.setTitle(getNodeValue(node));
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
		Node textNode = node.getChildNodes().item(0);
		
		return textNode != null ? textNode.getNodeValue() : "";
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
				feedItem.setTitle(getNodeValue(node));
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
				feedItem.setPubDate(getNodeValue(node));
				break;
				
			// TODO This is bad... possibly overwriting one or the other.
			case "summary":
				feedItem.setDescription(getNodeValue(node));
				break;
				
			case "content":
				feedItem.setDescription(getNodeValue(node));
				break;
		}
	}
}
