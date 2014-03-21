package pl.skawianna

import org.springframework.transaction.TransactionSystemException;

import pl.skawianna.beans.*
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(UserService)
class UserServiceSpec extends Specification {

    def "register unique user"() {
		setup:
		service.userHolder = new UserHolder()
		UserHolder.userSet.removeAll()
		
		when:
		service.registerUser("newName")
		
		then:
		UserHolder.userSet.size() == 1
    }
	
	
	def "register non-unique user"(){
		setup:
		service.userHolder = new UserHolder()
		UserHolder.userSet = new HashSet<User>()
		service.registerUser("newName")
		
		when:
		service.registerUser("newName")
		
		then:
		thrown TransactionSystemException
		UserHolder.userSet.size() == 1
	}
	
	def "list users alphabetically"(){
		setup:
		service.userHolder = new UserHolder()
		UserHolder.userSet = new HashSet<User>()
		service.registerUser("z")
		service.registerUser("a")
		service.registerUser("c")
		
		when:
		def users = service.getUsers()
		
		then:
		users.size() == 3
		users[0].username == "a"
		users[1].username == "c"
		users[2].username == "z"
	}
	
	def "set status to existing user"(){
		setup:
		service.userHolder = new UserHolder()
		UserHolder.userSet = new HashSet<User>()
		service.registerUser("ania")
		
		when:
		service.setStatus("ania", "status")
		
		then:
		UserHolder.userSet.size() == 1
		UserHolder.userSet.toList()[0].username == "ania"
		UserHolder.userSet.toList()[0].status == "status"
	}
	
	def "set status to non-existing user"(){
		setup:
		service.userHolder = new UserHolder()
		UserHolder.userSet = new HashSet<User>()
		
		when:
		service.setStatus("noname", "status")
		
		then:
		def users = service.getUsers()
		users.size() == 0
	}
	
}
