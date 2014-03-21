
package pl.skawianna
import org.joda.time.DateTime;
import org.joda.time.Period;

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ShoutboxService)
@Mock([Shoutbox])
class ShoutboxServiceSpec extends Specification {

	def setup() {
	}

	def cleanup() {
	}

	void "recent feed"() {
		setup:
		def justNow = new DateTime();

		new Shoutbox(timestamp: justNow.toDate(), author: "ania", content: "0").save()
		new Shoutbox(timestamp: justNow.minusMinutes(6).toDate(), author: "celina", content: "6").save()
		new Shoutbox(timestamp: justNow.minusMinutes(1).toDate(), author: "basia", content: "1").save()

		when:
		List<Shoutbox> recent = service.listSince(justNow.minus(new Period(0, 5, 0,0))) //5 mins
		
		then:
		recent.size() == 2
		recent[0].content == "1" 
		recent[1].content == "0" 
	}
}
