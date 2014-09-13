package org.jfr.data;

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
	 * The author.
	 */
	private String author;
	
	/**
	 * The GUID for the item.
	 */
	private String guid;
	
	/**
	 * The publish date for the item.
	 */
	private String pubDate;

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
	 * @return The value of author.
	 */
	public String getAuthor()
	{
		return author;
	}

	/**
	 *
	 * @param author The value of author.
	 */
	public void setAuthor(String author)
	{
		this.author = author;
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
				+ description + ", author=" + author + ", guid=" + guid
				+ ", pubDate=" + pubDate + "]";
	}
}
