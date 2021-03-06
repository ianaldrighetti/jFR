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
	 * The summary of the item.
	 */
	private String summary;
	
	/**
	 * The content of the item.
	 */
	private String content;
	
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
	private String published;
	
	/**
	 * The time the entry was updated.
	 */
	private String updated;

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
	public String getSummary()
	{
		return summary;
	}

	/**
	 *
	 * @param description The value of description.
	 */
	public void setSummary(String description)
	{
		this.summary = description;
	}

	/**
	 *
	 * @return The value of content.
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 *
	 * @param content The value of content.
	 */
	public void setContent(String content)
	{
		this.content = content;
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
	public String getPublished()
	{
		return published;
	}

	/**
	 *
	 * @param pubDate The value of pubDate.
	 */
	public void setPublished(String pubDate)
	{
		this.published = pubDate;
	}

	/**
	 * 
	 * @return The value of updated.
	 */
	public String getUpdated()
	{
		return updated;
	}

	/**
	 * 
	 * @param updated The value of updated.
	 */
	public void setUpdated(String updated)
	{
		this.updated = updated;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return "FeedItem [title=" + title + ", link=" + link + ", description="
				+ summary + ", authors=" + Arrays.toString(authors.toArray()) + ", guid=" + guid
				+ ", pubDate=" + published + ", updated = " + updated + "]";
	}
}
