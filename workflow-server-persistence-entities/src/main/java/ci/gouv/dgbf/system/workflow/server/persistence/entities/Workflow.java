package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.IOUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@Entity
public class Workflow extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable=false,unique=true)
	@NotNull
	private String code;
	
	@Column(nullable=false)
	@NotNull
	private String name;
	
	@Lob
	@Column(nullable=false)
	@NotNull
	private byte[] bytes;
	
	
	/**/
	
	public Workflow setBytesFromResourceAsStream(String name){
		try {
			setBytes(IOUtils.toByteArray(getClass().getResourceAsStream(name)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
}
