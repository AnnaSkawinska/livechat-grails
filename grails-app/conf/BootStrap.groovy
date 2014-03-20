import org.joda.time.DateTime

import pl.skawianna.Shoutbox

class BootStrap {

    def init = { servletContext ->
		
		for (int counter=0; counter<=20; counter++){
			new Shoutbox(author: "anonymous$counter", 
					content: "Hi all $counter", 
					timestamp: new DateTime().minusSeconds(counter * 10).toDate()
					).save(failOnError:true)
		}
		
    }
    def destroy = {
    }
}
