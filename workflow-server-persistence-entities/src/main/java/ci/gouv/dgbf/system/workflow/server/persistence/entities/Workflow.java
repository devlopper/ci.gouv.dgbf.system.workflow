package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.IOUtils;
import org.cyk.utility.server.persistence.jpa.AbstractEntity;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.bpmn.Bpmn;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@Entity @Access(AccessType.FIELD)
public class Workflow extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable=false,length=1024 * 100)
	@NotNull
	private String model;
	
	@Transient private Bpmn bpmn;
	
	/**/
	
	@Override
	public Workflow setCode(String code) {
		return (Workflow) super.setCode(code);
	}
	
	public Workflow setModel(String model){
		this.model = model;
		bpmn = Bpmn.__executeWithContent__(this.model);
		if(bpmn==null) {
			
		}else {
			setCode(bpmn.getProcess().getId());		
		}
		return this;
	}
	
	public Workflow setModelFromResourceAsStream(String name){
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(getClass().getResourceAsStream(name), "UTF-8");
			setModel(new String(IOUtils.toByteArray(inputStreamReader, "UTF-8")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	@Override
	public Workflow setIdentifier(Long identifier) {
		return (Workflow) super.setIdentifier(identifier);
	}
	
	/**/
	
	public String getName() {
		return bpmn == null ? null : bpmn.getProcess().getName();
	}
	
}
