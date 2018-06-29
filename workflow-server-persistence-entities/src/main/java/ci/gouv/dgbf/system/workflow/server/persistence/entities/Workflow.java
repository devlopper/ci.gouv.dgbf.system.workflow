package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.IOUtils;
import org.jbpm.services.api.model.ProcessDefinition;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@Entity
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
	@Column(nullable=false,unique=true)
	@NotNull
	private String code;
	
	/**
	 * Name of the workflow. This name must be equals to the one defined in the model
	 */
	@Column(nullable=false)
	@NotNull
	private String name;
	
	/**
	 * This is a helper object to read attributes
	 */
	@Transient
	private ProcessDefinition jbpmProcessDefinition;
	
	/**/
	
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
	
}
