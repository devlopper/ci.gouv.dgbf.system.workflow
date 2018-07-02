package ci.gouv.dgbf.system.workflow.server.representation.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

@Singleton
public class EntityMapperMap implements Serializable {
	private static final long serialVersionUID = 1L;

	private Map<Class<?>,EntityMapper<?,?>> map;
	
	public EntityMapperMap set(Class<?> aClass,EntityMapper<?,?> mapper){
		if(map == null)
			map = new HashMap<>();
		map.put(aClass, mapper);
		return this;
	}
	
	public EntityMapper<?,?> get(Class<?> aClass){
		return map == null ? null : map.get(aClass);
	}
}
