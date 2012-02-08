package org.neodatis.odb.core.query.nq;

import org.neodatis.odb.core.query.execution.IQueryExecutionPlan;

/**
 * 
 * This could be an improved query-base-type.
 *
 * @author Joerg Bellmann
 *
 * @param <T> type to match
 */
@SuppressWarnings("serial")
public abstract class TypedNativeQuery<T> extends SimpleNativeQuery {

    private T type;

    abstract public boolean match(T object);

    public Class<?> getObjectType() {
        return type.getClass();
    }

    public Class<?>[] getObjectTypes() {
        Class<?>[] classes = new Class[1];
        classes[0] = getObjectType();
        return classes;
    }

    public String[] getIndexFields() {
        return new String[0];
    }

    @Override
    public void setExecutionPlan(IQueryExecutionPlan plan) {
        executionPlan = plan;
    }

}
