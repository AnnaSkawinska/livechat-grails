package pl.skawianna

import java.text.SimpleDateFormat;

import javax.persistence.Transient;

import groovy.transform.ToString;


//@ToString
class Shoutbox {
	/**
	 * I tried using jodatime, but it won't go into MongoDB without doing some time-consuming magic. 
	 * mongo-joda-time plugin doesn't work.
	 */
	Date timestamp
	String author
	String content
    static constraints = {
    }
	
	@Transient
	SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
	
	public String toString(){
		
		return "[${hourFormat.format(timestamp)}] $author: $content" 
	}
	
}
