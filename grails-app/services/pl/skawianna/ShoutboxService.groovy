package pl.skawianna

import grails.transaction.Transactional

import org.joda.time.DateTime;
import org.joda.time.Period

@Transactional
class ShoutboxService {

    List<Shoutbox> listLastRecent(Period period){
		log.info("listLastRecent start - period: $period")
		List<Shoutbox> result = Shoutbox.findAllByTimestampGreaterThan(
			new DateTime().minus(period).toDate(), 
			[sort: "timestamp", order: "asc"])
		log.info("listLastRecent end - $result")
		return result
	}
}
