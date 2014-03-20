package pl.skawianna

import org.springframework.transaction.TransactionSystemException;

class UserService {
	/**
	 * shared in application scope - user list visible to all users
	 */
	private Set<User> userSet = new HashSet<User>()
	
	
	void registerUser(String username){
		log.info "registerUser start - username: $username"
		// yes, in Groovy the following is the same that String.equals():
		if (userSet.any{ user -> user.username == username}){
			throw new TransactionSystemException("User already exists")
		} else {
			userSet.add(new User(username: username))
		}
		log.info "registerUser end"
	}

	List <User> getUsers(){
		log.info "getUsers start"
		def result = userSet.toList().sort{it.username}
		log.info "getUsers end: $result"
		return result
	}
	
		
//	List <User> getUsers(){
//		log.info "getUsers start"
//		def result = User.list()
//		log.info "getUsers end: $result"
//		return result
//	}
//	
//	void registerUser(String username){
//		log.info "registerUser start - username: $username"
//		User user = new User(username: username, online:true)
//		User result = user.save()
//		log.info "registerUser end - $result"
//	}
	
}
