package org.jfr;

import java.io.File;

import org.jfr.JFRParser;
import org.jfr.data.Feed;
import org.jfr.data.FeedItem;

public class Program
{
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		JFRParser parser = new JFRParser();
		
		Feed feed = parser.parse(new File("C:\\Users\\Ian\\Desktop\\rss2sample.xml"));
		//Feed feed = parser.parse(new File("C:\\Users\\Ian\\Desktop\\atom2sample.xml"));
		//Feed feed = parser.parse(new File("C:\\Users\\Ian\\Desktop\\realsample.xml"));
		
		System.out.println("Title: " + feed.getTitle());
		System.out.println("Link: " + feed.getLink());
		System.out.println("Description: " + feed.getDescription());
		System.out.println("Items: " + feed.getItems().size());
		for (FeedItem feedItem : feed.getItems())
		{
			System.out.println(feedItem);
		}
	}
	
}
