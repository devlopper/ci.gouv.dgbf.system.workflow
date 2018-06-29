package ci.gouv.dgbf.system.workflow.business.impl;

import java.io.Serializable;

import javax.inject.Inject;

import ci.gouv.dgbf.system.workflow.business.api.Business;
import ci.gouv.dgbf.system.workflow.server.persistence.api.BusinessProcessModelNotationHelper;
import ci.gouv.dgbf.system.workflow.server.persistence.api.PersistenceHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractBusinessImpl implements Business, Serializable {
	private static final long serialVersionUID = 1L;

	@Inject protected PersistenceHelper persistenceHelper;
	@Inject protected BusinessProcessModelNotationHelper businessProcessModelNotationHelper;
	
}
