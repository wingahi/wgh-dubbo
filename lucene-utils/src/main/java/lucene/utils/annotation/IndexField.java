package lucene.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.lucene.document.Field;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IndexField {

	public Field.Store store();
	
	public Field.Index index();
}
