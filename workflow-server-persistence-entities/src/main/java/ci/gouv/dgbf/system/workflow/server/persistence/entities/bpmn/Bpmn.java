package ci.gouv.dgbf.system.workflow.server.persistence.entities.bpmn;

import java.io.FileInputStream;
import java.io.Serializable;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.io.IOUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement(name="definitions",namespace="http://www.omg.org/spec/BPMN/20100524/MODEL") @Getter @Setter @ToString
public class Bpmn implements Serializable {
	private static final long serialVersionUID = 1L;

	private Process process;
	
	/**/
	
	public String getProcessFirstUserTaskPotentialOwner(){
		return process == null ? null : process.getFirstUserTaskPotentialOwner();
	}
	
	/**/
	
	/**/
	
	
	public static Bpmn __execute__(String path) {
		Bpmn bpmnXml = null;
		try {
			String xml = IOUtils.toString(new FileInputStream(path),"UTF-8");
			bpmnXml = __executeWithContent__(xml);
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
		if(bpmnXml == null){
			//TODO log warning 
		}	
		return bpmnXml;
	}
	
	public static Bpmn __executeWithContent__(String content) {
		Bpmn bpmnXml = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Bpmn.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			bpmnXml = content == null ? null : (Bpmn) unmarshaller.unmarshal(new StringReader(content));
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
		if(bpmnXml == null){
			//TODO log warning 
		}	
		return bpmnXml;
	}
}
