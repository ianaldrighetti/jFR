package org.jfr.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a feed.
 *
 * @author Ian
 */
public class Feed
{
	/**
	 * The type of feed (RSS, Atom).
	 */
	private FeedType type;
	
	/**
	 * The title of the feed.
	 */
	private String title;
	
	/**
	 * The link indicating the source of the feed.
	 */
	private String link;
	
	/**
	 * The description of the feed.
	 */
	private String description;
	
	/**
	 * A list of items within the feed.
	 */
	private List<FeedItem> items;

	/**
	 * A list of authors/contributors of the feed.
	 */
	private List<Author> authors;
	
	/**
	 * Initializes the Feed.
	 */
	public Feed()
	{
		items = new ArrayList<>();
		authors = new ArrayList<>();
	}
	
	/**
	 *
	 * @return The value of type.
	 */
	public FeedType getType()
	{
		return type;
	}

	/**
	 *
	 * @param type The value of type.
	 */
	public void setType(FeedType type)
	{
		this.type = type;
	}

	/**
	 *
	 * @return The value of title.
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 *
	 * @param title The value of title.
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 *
	 * @return The value of link.
	 */
	public String getLink()
	{
		return link;
	}

	/**
	 *
	 * @param link The value of link.
	 */
	public void setLink(String link)
	{
		this.link = link;
	}

	/**
	 *
	 * @return The value of description.
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 *
	 * @param description The value of description.
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 *
	 * @return The value of items.
	 */
	public List<FeedItem> getItems()
	{
		return items;
	}

	/**
	 *
	 * @param items The value of items.
	 */
	public void setItems(List<FeedItem> items)
	{
		this.items = items;
	}
	
	/**
	 *
	 * @return The authors.
	 */
	public List<Author> getAuthors()
	{
		return authors;
	}

	/**
	 *
	 * @param author The author to add.
	 */
	public void addAuthor(Author author)
	{
		this.authors.add(author);
	}
	
	/**
	 * Sets the list of authors for the entry.
	 * 
	 * @param authors
	 */
	public void setAuthors(List<Author> authors)
	{
		this.authors = new ArrayList<>(authors);
	}
}
