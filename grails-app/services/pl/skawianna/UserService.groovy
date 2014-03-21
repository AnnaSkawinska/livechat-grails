package pl.skawianna

import org.springframework.transaction.TransactionSystemException;

class UserService {
	def userHolder
	
	
	void registerUser(String username){
		log.info "registerUser start - username: $username"
		// yes, in Groovy the following is the same that String.equals():
		if (userHolder.userSet.any{ user -> user.username == username}){
			throw new TransactionSystemException("User already exists")
		} else {
			userHolder.userSet.add(new User(username: username))
		}
		log.info "registerUser end"
	}

	List <User> getUsers(){
		log.info "getUsers start"
		def result = userHolder.userSet.toList().sort{it.username}
		log.info "getUsers end: $result"
		return result
	}
	
	void setStatus(String username, String status){
		log.info "setStatus start - username: $username, status: $status"
		User user = userHolder.userSet.find{ user -> user.username == username}
		user?.status = status
		log.info "setStatus end"
	}
	
	List<String> formatUsersWithStatuses(){
		log.info "formatUsersWithStatuses start"
		List<User> users = getUsers()
		
		List<String> result = users.collect{
			it.formatForTable()
		}
		log.info "formatUsersWithStatuses end - $result"
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
