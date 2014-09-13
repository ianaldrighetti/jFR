package org.jfr.parser;

import org.jfr.data.Feed;
import org.w3c.dom.Document;

/**
 * The interface all parsers must implement.
 *
 * @author Ian
 */
public interface FeedParserIF
{
	/**
	 * Parses the document according to it's format.
	 * 
	 * @param document The document to parse.
	 * @return The parsed feed.
	 */
	public Feed parse(Document document);
}
