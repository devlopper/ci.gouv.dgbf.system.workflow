package ci.gouv.dgbf.system.workflow.server.business.impl;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

import ci.gouv.dgbf.system.workflow.server.business.api.UserBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.api.UserPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.User;

@Singleton
public class UserBusinessImpl extends AbstractBusinessEntityImpl<User,UserPersistence> implements UserBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<User> __getEntityClass__() {
		return User.class;
	}
	
}
