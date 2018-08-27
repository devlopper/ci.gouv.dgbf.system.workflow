package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) 
@MappedSuperclass @Deprecated
public abstract class AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * System identifier
	 */
	@Id @GeneratedValue
	protected Long identifier;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
	
	/**/
	
	public static final String FIELD_IDENTIFIER = "identifier";
}
