package pl.skawianna

import grails.transaction.Transactional

@Transactional
class UserService {
	
	List <User> getUsers(){
		log.info "getUsers start"
		def result = User.list()
		log.info "getUsers end: $result"
		return result
	}
	
	void registerUser(String username){
		log.info "registerUser start - username: $username"
		User user = new User(username: username, online:true)
		User result = user.save()
		log.info "registerUser end - $result"
	}
	
}
