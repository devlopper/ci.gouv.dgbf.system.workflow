package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.jpa.AbstractPersistenceEntityImpl;
import org.cyk.utility.value.ValueUsageType;

import ci.gouv.dgbf.system.workflow.server.persistence.api.UserPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.User;

@Singleton
public class UserPersistenceImpl extends AbstractPersistenceEntityImpl<User> implements UserPersistence,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public User readOne(Object identifier, Properties properties) {
		ValueUsageType valueUsageType = (ValueUsageType) Properties.getFromPath(properties, Properties.VALUE_USAGE_TYPE);
		if(ValueUsageType.BUSINESS.equals(valueUsageType)) {
			Collection<User> users = readMany();
			if(users!=null)
				for(User index : users)
					if(index.getCode().equals(identifier))
						return index;
		}
		return super.readOne(identifier, properties);
	}
	
	@Override
	public Collection<User> readMany(Properties properties) {
		Collection<String> userIdentifiers = __inject__(JbpmHelper.class).getUserIdentifiers();
		Collection<User> users = null;
		if(userIdentifiers!=null) {
			users = new LinkedHashSet<>();
			for(String index : userIdentifiers) 	
				users.add(new User().setCode(index));
		}
		return users;
	}
	
	@Override
	public Long count(Properties properties) {
		return __injectCollectionHelper__().getSize(readMany(properties)).longValue();
	}
	
}
