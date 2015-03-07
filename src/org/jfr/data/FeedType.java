package org.jfr.data;

/**
 * The type of feed.
 * 
 * @author Ian
 */
public enum FeedType
{
	RSS ("RSS"), Atom ("Atom");
	
	private String label;
	
	private FeedType(String label)
	{
		this.label = label;
	}
	
	public String getLabel()
	{
		return label;
	}
}
