package pl.skawianna

import grails.transaction.Transactional

@Transactional
class UserService {

	List <User> getUsers(){
		return User.list()
	}
}
