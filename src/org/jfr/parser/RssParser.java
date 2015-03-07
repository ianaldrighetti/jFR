package org.jfr.parser;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.jfr.data.Author;
import org.jfr.data.AuthorType;
import org.jfr.data.Feed;
import org.jfr.data.FeedItem;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Implements the parser for an RSS feed.
 *
 * @author Ian
 */
public class RssParser implements FeedParserIF
{
	/**
	 * Parses the document using the expected RSS format.
	 */
	@Override
	public Feed parse(Document document)
	{
		// Get the root node.
		Node rssElement = document.getFirstChild();
		
		NamedNodeMap attrMap = rssElement.getAttributes();
		System.out.println("version = " + attrMap.getNamedItem("version").getNodeValue());
		
		// Now get channel.
		NodeList childNodes = rssElement.getChildNodes();
		Node channelNode = null;
		for (int index = 0; index < childNodes.getLength(); index++)
		{
			Node childNode = childNodes.item(index);
			
			if (!childNode.getNodeName().equalsIgnoreCase("channel"))
			{
				continue;
			}
			
			channelNode = childNode;
		}
		
		if (channelNode == null)
		{
			// TODO: Exception
			throw new IllegalArgumentException("channel not found");
		}
		
		// We've done enough here...
		return parseChannel(channelNode);
	}
	
	/**
	 * Parses the channel tag within an RSS feed.
	 * 
	 * @param channelNode
	 * @return
	 */
	private Feed parseChannel(Node channelNode)
	{
		Feed feed = new Feed();
		NodeList childNodes = channelNode.getChildNodes();
		
		for (int index = 0; index < childNodes.getLength(); index++)
		{
			Node childNode = childNodes.item(index);
			
			// Within channel, we don't want to deal with text nodes.
			if (childNode.getNodeType() == Node.TEXT_NODE)
			{
				continue;
			}
			
			parseNode(feed, childNode);
		}
		
		return feed;
	}

	/**
	 * Handles a Node (determining whether it is a title, description, item, etc.
	 * and handling it appropriately).
	 * 
	 * @param feed The feed to set any values to.
	 * @param node The node.
	 */
	private void parseNode(Feed feed, Node node)
	{
		switch(node.getNodeName().toLowerCase())
		{
			case "title":
				feed.setTitle(getNodeValue(node));
				break;
			
			case "description":
				feed.setDescription(getNodeValue(node));
				break;
			
			case "link":
				feed.setLink(getNodeValue(node));
				break;
			
			case "item":
				parseItem(feed, node);
		}
	}
	
	/**
	 * Obtains the text value of the node.
	 * 
	 * @param node The node.
	 * @return The text value.
	 */
	private String getNodeValue(Node node)
	{
		// TODO: check escape requirements in RSS spec.
		Node textNode = node.getChildNodes().item(0);
		
		return textNode != null ? textNode.getNodeValue() : "";
	}
	
	/**
	 * Parses a node representing an item.
	 * 
	 * @param feed
	 * @param node
	 */
	private void parseItem(Feed feed, Node node)
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
			
			parseItemNode(feedItem, childNode);
		}
		
		feed.getItems().add(feedItem);
	}
	
	/**
	 * Determines whether the node contains any relevant information to set on the feed item.
	 * 
	 * @param feedItem
	 * @param node
	 */
	private void parseItemNode(FeedItem feedItem, Node node)
	{
		switch (node.getNodeName().toLowerCase())
		{
			case "title":
				feedItem.setTitle(getNodeValue(node));
				break;
			
			case "link":
				feedItem.setLink(getNodeValue(node));
				break;
			
			case "description":
				feedItem.setDescription(getNodeValue(node));
				break;
			
			case "pubdate":
				feedItem.setPubDate(getNodeValue(node));
				break;
			
			case "guid":
				feedItem.setGuid(getNodeValue(node));
				break;
			
			case "author":
				feedItem.addAuthor(getAuthor(getNodeValue(node)));
				break;
		}
	}
	
	/**
	 * Translates a string into an Author. This will attempt to parse the string
	 * into a name and email. For example, if the string is "Ian (me@outlook.com)"
	 * and instance of Author with the name of Ian and email of me@outlook.com is returned.
	 * 
	 * @param text
	 * @return Author
	 */
	private Author getAuthor(String text)
	{
		Author author = new Author();
		
		// RSS has no other type but author.
		author.setType(AuthorType.Author);
		
		// There might be an email or address in parenthesis.
		if (text.contains("(") && text.contains(")"))
		{
			// Get the text between the parenthesis.
			String parenthesis = text.substring(text.indexOf('(') + 1, text.lastIndexOf(')'));
			
			setAuthorAttribute(author, parenthesis.trim());

			// Now we need to remove that from the text.
			text = text.replace("(" + parenthesis + ")", "");
		}
		
		setAuthorAttribute(author, text.trim());
		
		return author;
	}
	
	/**
	 * This method determines what text likely represents and sets the
	 * proper attribute on the Author.
	 * 
	 * @param author
	 * @param text
	 */
	private void setAuthorAttribute(Author author, String text)
	{
		EmailValidator emailValidator = EmailValidator.getInstance(false);
		
		// Check if it is a valid email.
		if (emailValidator.isValid(text))
		{
			// It is, so set it there.
			author.setEmail(text);
			
			return;
		}
		
		UrlValidator urlValidator = UrlValidator.getInstance();
		
		// Now check if it is a URL.
		if (urlValidator.isValid(text))
		{
			author.setUrl(text);
			
			return;
		}
		
		// If all else fails, then it will just be a name!
		author.setName(text);
	}
}
