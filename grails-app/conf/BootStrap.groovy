import pl.skawianna.User

class BootStrap {

    def init = { servletContext ->
		
//		if (Environment.current == Environment.DEVELOPMENT) {
			User ania = new User(username: 'ania', password: 'ania')
			ania.save(failOnError: true)
//		}
		
    }
    def destroy = {
    }
}
