package com.ots.dpel.global.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility methods for working with objects using reflection.
 * 
 * @author ktzonas
 *
 */
public class ReflectionUtils {

    /**
     * Returns all the declared non-static fields of the given class, including
     * its superclasses, as a {@link Map} of field names to {@link Field}
     * instances.
     * 
     * @param clazz
     *            The class whose fields will be retrieved
     * @return
     */
    public static Map<String, Field> getFieldsMap(Class<?> clazz) {
        Map<String, Field> fields = new HashMap<String, Field>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!fields.containsKey(field.getName()) && !Modifier.isStatic(field.getModifiers())) {
                    fields.put(field.getName(), field);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /**
     * Returns the (non-static) field with name {@code fieldName} in the class
     * {@code clazz}.
     * 
     * @param clazz
     *            The class in which whose fields are searched
     * @param fieldName
     *            the name of the field in question
     * @return instance of {@link Field}
     */
    public static Field getField(Class<?> clazz, String fieldName) {

        if (clazz == null || fieldName == null) {
            return null;
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (fieldName.equals(field.getName()) && !Modifier.isStatic(field.getModifiers())) {
                return field;
            }
        }

        return null;
    }

    /**
     * Returns all the declared non-static fields of the given class as a
     * {@link Collection} of {@link Field}s.
     * 
     * @param clazz
     *            The class whose fields will be retrieved
     * @return
     */
    public static Collection<Field> getFieldsCollection(Class<?> clazz) {
        return getFieldsMap(clazz).values();
    }

    /**
     * Returns the value of the field with name {@code fieldName} from the
     * object {@code obj}, with the type declared in {@code fieldType}.
     * 
     * @param obj
     *            The object from which the field value will be retrieved
     * @param fieldName
     *            The name of the field to inspect
     * @param fieldType
     *            The type of the field value
     * @return An instance of the type specified in {@code fieldType}, if the
     *         field is found in the object. If any error occurs, null is
     *         returned
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Object obj, String fieldName, Class<T> fieldType) {
        try {
            Field field = getFieldsMap(obj.getClass()).get(fieldName);
            field.setAccessible(true);
            return (T) field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves and returns the values of the non-static fields for the given
     * object.
     * 
     * @param obj
     *            The object to inspect
     * @return A {@link Map} of field names to field values
     */
    public static Map<String, Object> getFieldValueMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Field> objectFields = getFieldsMap(obj.getClass());
        for (Map.Entry<String, Field> entry : objectFields.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = getFieldValue(obj, fieldName, entry.getValue().getClass());
            map.put(fieldName, fieldValue);
        }
        return map;
    }

    /**
     * Extracts the values of the field with name {@code fieldName}, whose type
     * is specified in {@code fieldType}, for all the objects in the
     * {@code objects} collection.
     * 
     * @param objects
     *            A collection of objects
     * @param fieldName
     *            The name of the field to inspect
     * @param fieldType
     *            The type of the field value
     * @return A {@link List} of objects of the type specified in
     *         {@code fieldType}
     */
    public static <T> List<T> extractFieldValuesToList(Collection<? extends Object> objects, String fieldName,
            Class<T> fieldType) {
        List<T> list = new ArrayList<>();
        if (objects != null && !objects.isEmpty()) {
            for (Object obj : objects) {
                list.add(getFieldValue(obj, fieldName, fieldType));
            }
        }
        return list;
    }

    /**
     * Extracts the values of the field with name {@code id} with type
     * {@code Long}, for all the objects in the {@code objects} collection.
     * 
     * @param objects
     * @return
     */
    public static List<Long> extractIdList(Collection<? extends Object> objects) {
        return extractFieldValuesToList(objects, "id", Long.class);
    }

    /**
     * Extracts the values of the field with name {@code fieldName}, whose type
     * is specified in {@code fieldType}, for all the objects in the
     * {@code objects} collection.
     * 
     * @param objects
     *            A collection of objects
     * @param fieldName
     *            The name of the field to inspect
     * @param fieldType
     *            The type of the field value
     * @return A {@link Collection} of objects of the type specified in
     *         {@code fieldType}
     */
    public static <T> Collection<T> extractFieldValuesToCollection(Collection<? extends Object> objects,
            String fieldName, Class<T> fieldType) {
        return extractFieldValuesToList(objects, fieldName, fieldType);
    }

    /**
     * Extracts the values of the field with name {@code id} with type
     * {@code Long}, for all the objects in the {@code objects} collection.
     * 
     * @param objects
     * @return
     */
    public static Collection<Long> extractIdCollection(Collection<? extends Object> objects) {
        return extractFieldValuesToList(objects, "id", Long.class);
    }

    /**
     * Creates a {@link Map} where, for each of the given {@code objects}, the
     * key is computed by extracting the value of the field with name
     * {@code fieldName} (whose type must be specified in {@code fieldType}),
     * and the value is the original object.
     * 
     * <p>
     * For example, if the input objects are the following (using pseudo-JSON
     * syntax):
     * </p>
     * 
     * <pre>
     * [
     *   {id: 9, name: 'alice', salary: 1000}, 
     *   {id: 8, name: 'bob', salary: 500}, 
     *   {id: 12, name: 'charlie', salary: 200}
     * ]
     * </pre>
     * 
     * then the output of
     * {@code indexObjectsByFieldValue(objects, 'name', String.class)} would be:
     * 
     * <pre>
     * {
     *   "alice": {id: 9, name: 'alice', salary: 1000},
     *   "bob": {id: 8, name: 'bob', salary: 500},
     *   "charlie": {id: 12, name: 'charlie', salary: 200}
     * }
     * </pre>
     * 
     * while the output of
     * {@code indexObjectsByFieldValue(objects, 'id', Long.class)} would be:
     * 
     * <pre>
     * {
     *   9: {id: 9, name: 'alice', salary: 1000},
     *   8: {id: 8, name: 'bob', salary: 500},
     *   12: {id: 12, name: 'charlie', salary: 200}
     * }
     * </pre>
     * 
     * @param objects
     * @param fieldName
     * @param fieldType
     * @return
     */
    public static <K, V> Map<K, V> indexObjectsByFieldValue(Collection<V> objects, String fieldName, Class<K> fieldType,
            Boolean dismissDoubles) {
        Map<K, V> map = new HashMap<>();

        for (V obj : objects) {
            K fieldValue = getFieldValue(obj, fieldName, fieldType);
            Boolean putEntry = true;
            if (dismissDoubles && map.containsKey(fieldValue)) {
                putEntry = false;
            }

            if (putEntry) {
                map.put(fieldValue, obj);
            }
        }
        return map;
    }

    public static <K, V> Map<K, V> indexObjectsByFieldValue(Collection<V> objects, String fieldName,
            Class<K> fieldType) {
        return indexObjectsByFieldValue(objects, fieldName, fieldType, false);
    }

    /**
     * Creates a {@link Map} where, for each of the given {@code objects}, the
     * key is computed by extracting the object ID (of type {@code Long}), and
     * the value is the original object.
     * 
     * @param objects
     * @return
     */
    public static <V> Map<Long, V> indexObjectsById(Collection<V> objects) {
        return indexObjectsByFieldValue(objects, "id", Long.class);
    }
}
