package pl.skawianna

import grails.transaction.Transactional

import org.joda.time.DateTime;
import org.joda.time.Period

@Transactional
class ShoutboxService {

	List<Shoutbox> listSince(DateTime timestamp){
		log.info("listSince start - timestamp: $timestamp")
		List<Shoutbox> result = Shoutbox.findAllByTimestampGreaterThan(
			timestamp.toDate(), [sort: "timestamp", order: "asc"])
		log.info("listSince end - $result")
		return result
	}
	
	void sendMessage(String message, String author){
		log.info("sendMessage start - author: $author, message: $message")
		
		Shoutbox result = new Shoutbox(author: author, content: message, timestamp: new Date()).save()
		
		log.info("sendMessage end - $result")
	}
}
