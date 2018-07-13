package ci.gouv.dgbf.system.workflow.client.controller.impl;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.dgbf.system.workflow.client.controller.api.WorkflowForm;
import ci.gouv.dgbf.system.workflow.client.controller.entities.AbstractEditorPage;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Named @ViewScoped
public class WorkflowEditorPage extends AbstractEditorPage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*@Inject @New*/ private WorkflowForm form = new WorkflowFormImpl();
	
}
