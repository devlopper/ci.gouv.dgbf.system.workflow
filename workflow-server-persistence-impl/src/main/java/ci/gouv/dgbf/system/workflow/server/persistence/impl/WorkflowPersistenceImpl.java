package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;

import javax.persistence.NoResultException;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

public class WorkflowPersistenceImpl extends AbstractEntityPersistenceImpl<Workflow> implements WorkflowPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Workflow readByCode(String code) {
		try {
			return entityManager.createQuery("SELECT workflow FROM Workflow workflow WHERE workflow.code = :workflowCode",Workflow.class)
					.setParameter("workflowCode", code).getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}

	@Override
	public WorkflowPersistence deleteByCode(String code) {
		delete(readByCode(code));
		return this;
	}
}
