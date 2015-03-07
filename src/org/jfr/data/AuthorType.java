package org.jfr.data;

/**
 * Used for indicating the type of author.
 *
 * @author Ian
 */
public enum AuthorType
{
	/**
	 * The author is a regular author.
	 */
	Author ("Author"),
	
	/**
	 * They are a contributor to the article.
	 */
	Contributor ("Contributor");
	
	/**
	 * The author type label.
	 */
	private String type;
	
	private AuthorType(String type)
	{
		this.type = type;
	}
	
	/**
	 * Returns the author type label.
	 * 
	 * @return Author or Contributor.
	 */
	public String getType()
	{
		return this.type;
	}
}
