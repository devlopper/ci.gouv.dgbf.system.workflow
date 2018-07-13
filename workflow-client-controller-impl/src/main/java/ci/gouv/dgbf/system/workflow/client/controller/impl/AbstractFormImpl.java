package ci.gouv.dgbf.system.workflow.client.controller.impl;


import java.io.Serializable;

import ci.gouv.dgbf.system.workflow.client.controller.api.Form;
import ci.gouv.dgbf.system.workflow.client.controller.entities.AbstractData;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractFormImpl<DATA extends AbstractData> implements Form<DATA>,Serializable {
	private static final long serialVersionUID = 1L;
	
	private DATA data;
	
}
