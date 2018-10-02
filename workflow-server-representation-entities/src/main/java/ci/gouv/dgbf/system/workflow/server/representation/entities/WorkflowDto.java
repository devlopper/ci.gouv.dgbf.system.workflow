package ci.gouv.dgbf.system.workflow.server.representation.entities;

import java.io.IOException;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.server.representation.AbstractEntityFromPersistenceEntityDto;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.bpmn.Bpmn;

@XmlRootElement
@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true) @lombok.NoArgsConstructor
public class WorkflowDto extends AbstractEntityFromPersistenceEntityDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String model;
	
	@Override
	public WorkflowDto setCode(String code) {
		return (WorkflowDto) super.setCode(code);
	}
	
	public WorkflowDto setModel(String model){
		this.model = model;
		setCodeAndNameFromModel();
		return this;
	}
	
	public WorkflowDto setModelFromResourceAsStream(String name){
		try {
			setModel(new String(IOUtils.toByteArray(getClass().getResourceAsStream(name))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public WorkflowDto setCodeAndNameFromModel(){
		if(StringUtils.isBlank(model)) {
			setCode(null);
			setName(null);
		}else {
			Bpmn bpmn = Bpmn.__executeWithContent__(model);
			if(bpmn.getProcess()!=null) {
				setCode(bpmn.getProcess().getId());
				setName(bpmn.getProcess().getName());
			}
		}
		return this;
	}
	
	@Override
	public String toString() {
		return super.toString()+" , "+FIELD_NAME+"="+name+" , "+FIELD_MODEL+"="+model;
	}
	
	/**/
	
	public static final String FIELD_NAME = "name";
	public static final String FIELD_MODEL = "model";
}
