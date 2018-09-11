package ci.gouv.dgbf.system.workflow.client.controller.api;

import ci.gouv.dgbf.system.workflow.client.controller.entities.AbstractData;

public interface Form<DATA extends AbstractData> {

	Form<DATA> setData(DATA data);
	DATA getData();
	
	void submit();
	
}
