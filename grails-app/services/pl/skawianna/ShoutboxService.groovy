package pl.skawianna

import grails.transaction.Transactional

import org.joda.time.DateTime;
import org.joda.time.Period

@Transactional
class ShoutboxService {

    List<Shoutbox> listLast(Period period){
		Shoutbox.findAllByTimestampGreaterThan(new DateTime().minus(period), 
			[sort: "timestamp", order: "asc"])
	}
}
