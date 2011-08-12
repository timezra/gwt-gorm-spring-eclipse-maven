package timezra.blog.gorm.domain

abstract class DomainObject implements Serializable {

	Date dateCreated
	Date lastUpdated

	static constraints = {
		dateCreated()
		lastUpdated()
	}
}
