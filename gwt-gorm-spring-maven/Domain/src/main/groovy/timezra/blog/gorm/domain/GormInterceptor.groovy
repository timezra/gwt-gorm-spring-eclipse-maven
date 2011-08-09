package timezra.blog.gorm.domain

import java.io.Serializable

import org.hibernate.EmptyInterceptor
import org.hibernate.type.Type

class GormInterceptor extends EmptyInterceptor {

	@Override
	boolean onSave(Object entity, Serializable id, Object[] state,
	String[] propertyNames, Type[] types) {
		def now = new Date();
		return setValue(state, propertyNames, "dateCreated", now) | setValue(state, propertyNames, "lastUpdated", now)
	}

	@Override
	boolean onFlushDirty(Object entity, Serializable id,
	Object[] currentState, Object[] previousState,
	String[] propertyNames, Type[] types) {
		return setValue(currentState, propertyNames, "lastUpdated", new Date())
	}

	private boolean setValue(Object[] currentState, String[] propertyNames,
	String propertyToSet, def value) {
		def index = propertyNames.toList().indexOf(propertyToSet)
		if (index >= 0) {
			currentState[index] = value
			return true
		}
		return false
	}
}