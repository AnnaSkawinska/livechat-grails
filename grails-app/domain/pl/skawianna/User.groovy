package pl.skawianna

import groovy.transform.ToString;

@ToString
class User {
	/**
	 * Not a persistent class - application users kept in application scope
	 */
	static mapWith = "none"
	String username
	String status = ""
	
    static constraints = {
		status nullable:true
//		username index:true, indexAttributes: [unique:true]
    }
	
	public String formatForTable(){
		"$username \n <i>${status ?: ''}</i>".toString()
	}
}
