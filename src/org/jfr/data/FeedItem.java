package org.jfr.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents an item within the feed.
 * 
 * @author Ian
 */
public class FeedItem
{
	/**
	 * The title of the item.
	 */
	private String title;
	
	/**
	 * The link to the source of the item.
	 */
	private String link;
	
	/**
	 * The description of the item.
	 */
	private String description;
	
	/**
	 * The author(s).
	 */
	private List<Author> authors;
	
	/**
	 * The GUID for the item.
	 */
	private String guid;
	
	/**
	 * The publish date for the item.
	 */
	private String pubDate;

	/**
	 * Initializes the FeedItem.
	 */
	public FeedItem()
	{
		this.authors = new ArrayList<Author>();
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

	/**
	 *
	 * @return The value of guid.
	 */
	public String getGuid()
	{
		return guid;
	}

	/**
	 *
	 * @param guid The value of guid.
	 */
	public void setGuid(String guid)
	{
		this.guid = guid;
	}

	/**
	 *
	 * @return The value of pubDate.
	 */
	public String getPubDate()
	{
		return pubDate;
	}

	/**
	 *
	 * @param pubDate The value of pubDate.
	 */
	public void setPubDate(String pubDate)
	{
		this.pubDate = pubDate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return "FeedItem [title=" + title + ", link=" + link + ", description="
				+ description + ", authors=" + Arrays.toString(authors.toArray()) + ", guid=" + guid
				+ ", pubDate=" + pubDate + "]";
	}
}
