package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceServiceProvider;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.server.persistence.query.PersistenceQueryRepository;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;
import org.jbpm.services.api.DefinitionService;

import ci.gouv.dgbf.system.workflow.server.persistence.api.PersistenceHelper;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

@Singleton
public class WorkflowPersistenceImpl extends AbstractPersistenceEntityImpl<Workflow> implements WorkflowPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private PersistenceHelper persistenceHelper;
	private String readByCode;
	@Inject private DefinitionService definitionService;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readByCode, __instanciateQuerySelect__()
				.getWherePredicateBuilderAsEqual().addOperandBuilderByAttribute(Workflow.FIELD_CODE,ComparisonOperator.EQ)
				.getParentAsWhereClause().getParentAs(QueryStringBuilderSelect.class));
	}
	
	protected Object[] __getQueryParameters__(String queryIdentifier,Object...objects){
		PersistenceQuery persistenceQuery = __inject__(PersistenceQueryRepository.class).getBySystemIdentifier(queryIdentifier);
		
		if(persistenceQuery.isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByCode,queryIdentifier))
			return new Object[]{Workflow.FIELD_CODE,objects[0]};
		
		return super.__getQueryParameters__(queryIdentifier, objects);
	}
	
	@Override
	public Workflow readByCode(String code) {
		Workflow workflow = __injectCollectionHelper__().getFirst(__readMany__(____getQueryParameters____(code)));
		//TODO to be removed. Workflows must be auto loaded
		definitionService.buildProcessDefinition("MYDEP", workflow.getModel(), null, Boolean.FALSE);
		return workflow;
	}
	
	@Override
	public PersistenceServiceProvider<Workflow> create(Workflow workflow, Properties properties) {
		workflow.setCodeAndNameFromModel();
		super.create(workflow, properties);
		//TODO to be factored some where. Use a function to add created workflow to JBPM repository 
		definitionService.buildProcessDefinition("MYDEP", workflow.getModel(), null, Boolean.FALSE);
		persistenceHelper.addProcessDefinitionsFromWorkflow(workflow).buildRuntimeEnvironment();
		return this;
	}
	
	/*@Override
	public Workflow readByCode(String code) {
		try {
			Workflow workflow = entityManager.createQuery("SELECT workflow FROM Workflow workflow WHERE workflow.code = :workflowCode",Workflow.class)
					.setParameter("workflowCode", code).getSingleResult();
			if(workflow!=null){
				if(workflow.getJbpmProcessDefinition() == null)
					buildProcessDefinition(workflow);
			}
			return workflow;
		} catch (NoResultException exception) {
			return null;
		}
	}*/
	
	@Override
	public PersistenceServiceProvider<Workflow> update(Workflow workflow, Properties properties) {
		workflow.setCodeAndNameFromModel();
		return super.update(workflow, properties);
	}
	
	/*@Override
	public EntityPersistence<Workflow> update(Workflow workflow) {
		if(workflow.getJbpmProcessDefinition() == null)
			buildProcessDefinition(workflow);
		workflow.setCode(workflow.getJbpmProcessDefinition().getId());
		workflow.setName(workflow.getJbpmProcessDefinition().getName());
		super.update(workflow);
		//TODO we should update the bytes
		//persistenceHelper.addProcessDefinitionsFromWorkflow(workflow);
		return this;
	}*/

	@Override
	public WorkflowPersistence deleteByCode(String code) {
		delete(readByCode(code));
		return this;
	}
	
	/**/

}
