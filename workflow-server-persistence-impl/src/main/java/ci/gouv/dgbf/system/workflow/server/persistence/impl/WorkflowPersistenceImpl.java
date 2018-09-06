package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.server.persistence.jpa.AbstractPersistenceEntityImpl;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

@Singleton
public class WorkflowPersistenceImpl extends AbstractPersistenceEntityImpl<Workflow> implements WorkflowPersistence,Serializable {
	private static final long serialVersionUID = 1L;
	
}
