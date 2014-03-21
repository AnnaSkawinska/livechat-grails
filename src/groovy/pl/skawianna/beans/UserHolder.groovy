package pl.skawianna.beans
import java.util.Set;

import pl.skawianna.User;


class UserHolder {
	/**
	 * shared in application scope - user list visible to all users
	 */
	public static Set<User> userSet = new HashSet<User>()
}
