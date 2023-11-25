package common.Serialisation;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * stratergy to exclude any fields with ExcludeSerialisation Annotation
 * @author vishnurajendran
 */
public class ExStrat implements ExclusionStrategy {
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes field) {
        return field.getAnnotation(ExcludeSerialisation.class) != null;
    }
}
