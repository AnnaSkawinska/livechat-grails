package pl.skawianna

import grails.transaction.Transactional

@Transactional
class UserService {

	List <User> getUsers(){
		return User.list()
	}
	
	void addOnlineUser(String username){
		User user = new User(username: username, online:true)
		user.save()
	}
}
