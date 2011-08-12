package timezra.blog.gorm.domain

import grails.persistence.Entity

@Entity
class User extends DomainObject {

	String name

	static constraints = {
		name(size:3..30,blank:false,unique:true)
	}

	String toString() {
		"User ${name} (${id})"
	}
}