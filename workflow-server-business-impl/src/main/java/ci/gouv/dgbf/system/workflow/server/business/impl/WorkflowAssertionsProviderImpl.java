package ci.gouv.dgbf.system.workflow.server.business.impl;

import java.io.Serializable;

import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.assertion.AbstractAssertionsProviderForImpl;
import org.cyk.utility.server.business.BusinessFunction;
import org.cyk.utility.server.business.BusinessFunctionCreator;

import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowAssertionsProvider;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

public class WorkflowAssertionsProviderImpl extends AbstractAssertionsProviderForImpl<Workflow> implements WorkflowAssertionsProvider,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void ____execute____(Function<?,?> function,Object filter, Workflow workflow) {
		if(function instanceof BusinessFunctionCreator) {
			if(filter==null) {
				workflow = (Workflow) ((BusinessFunction)function).getEntity();
				if(!workflow.getCode().equals(workflow.getBpmn().getProcess().getId()))
					__add__(__inject__(Assertion.class).setValue(Boolean.FALSE).setMessageWhenValueIsNotTrue("code("+workflow.getCode()+") must be equal to process id("+workflow.getBpmn().getProcess().getId()+")"));
			}
		}else {
			
		}
	}
	
}