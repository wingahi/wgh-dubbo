package lucene.utils.entity;

import org.apache.lucene.document.Field;

public class EntityValueMapper {

	private Object value;
	
	private Field.Store store;
	
	private Field.Index index;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Field.Store getStore() {
		return store;
	}

	public void setStore(Field.Store store) {
		this.store = store;
	}

	public Field.Index getIndex() {
		return index;
	}

	public void setIndex(Field.Index index) {
		this.index = index;
	}
	
	
}
