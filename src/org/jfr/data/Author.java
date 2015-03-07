package org.jfr.data;

/**
 * Represents an author.
 *
 * @author Ian
 */
public class Author
{
	private String name;
	
	private String email;
	
	private String url;
	
	private AuthorType type;
	
	/**
	 * 
	 */
	public Author()
	{
		
	}
	
	/**
	 * Sets the author details.
	 * 
	 * @param name
	 * @param email
	 * @param url
	 * @param type
	 */
	public Author(String name, String email, String url, AuthorType type)
	{
		this.name = name;
		this.email = email;
		this.url = url;
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public AuthorType getType()
	{
		return type;
	}

	public void setType(AuthorType type)
	{
		this.type = type;
	}

	@Override
	public String toString()
	{
		return "Author [name=" + name + ", email=" + email + ", url=" + url
				+ ", type=" + type + "]";
	}
}
