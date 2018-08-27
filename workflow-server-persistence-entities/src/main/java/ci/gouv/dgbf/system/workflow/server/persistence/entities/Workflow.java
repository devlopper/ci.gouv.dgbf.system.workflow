package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
	
	/*
	 * Following read only value from model
	 */
	
	/**
	 * Code of the workflow. This code must be equals to the one defined in the model
	 */

	/**
	 * Name of the workflow. This name must be equals to the one defined in the model
	 */
	@Column(nullable=false)
	@NotNull
	private String name;
	
	/**/
	
	@Override
	public Workflow setCode(String code) {
		return (Workflow) super.setCode(code);
	}
	
	public Workflow setModel(String model){
		this.model = model;
		setCodeAndNameFromModel();
		return this;
	}
	
	public Workflow setModelFromResourceAsStream(String name){
		try {
			setModel(new String(IOUtils.toByteArray(getClass().getResourceAsStream(name))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	@Override
	public Workflow setIdentifier(Long identifier) {
		return (Workflow) super.setIdentifier(identifier);
	}
	
	public Workflow setCodeAndNameFromModel(){
		Bpmn bpmn = Bpmn.__executeWithContent__(model);
		if(bpmn.getProcess()!=null) {
			setCode(bpmn.getProcess().getId());
			setName(bpmn.getProcess().getName());	
		}
		return this;
	}
}
