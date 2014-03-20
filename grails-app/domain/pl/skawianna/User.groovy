package pl.skawianna

class User {
	String username
	Boolean online = false
	String status = ""
	
    static constraints = {
		status nullable:true
		username index:true, indexAttributes: [unique:true]
    }
}
