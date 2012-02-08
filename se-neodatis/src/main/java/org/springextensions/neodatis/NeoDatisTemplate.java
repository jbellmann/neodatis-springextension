/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springextensions.neodatis;

import java.math.BigInteger;

import org.neodatis.odb.ClassRepresentation;
import org.neodatis.odb.DatabaseId;
import org.neodatis.odb.ExternalOID;
import org.neodatis.odb.ODB;
import org.neodatis.odb.OID;
import org.neodatis.odb.Objects;
import org.neodatis.odb.TransactionId;
import org.neodatis.odb.Values;
import org.neodatis.odb.core.layers.layer3.IRefactorManager;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.IValuesQuery;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.trigger.DeleteTrigger;
import org.neodatis.odb.core.trigger.InsertTrigger;
import org.neodatis.odb.core.trigger.SelectTrigger;
import org.neodatis.odb.core.trigger.UpdateTrigger;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.springframework.dao.DataAccessException;

/**
 * 
 * @author Joerg Bellmann
 *
 */
public class NeoDatisTemplate extends NeoDatisAccessor implements NeoDatisOperations {

    public NeoDatisTemplate() {
        //
    }

    public NeoDatisTemplate(ODB odb) {
        setOdb(odb);
        afterPropertiesSet();
    }

    @Override
    public Object execute(NeoDatisCallback callback) throws DataAccessException {
        return callback.doInNeoDatis(getOdb());
    }

    @Override
    public void addDeleteTrigger(final Class clazz, final DeleteTrigger deleteTrigger) {
        execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                odb.addDeleteTrigger(clazz, deleteTrigger);
                return null;
            }
        });
    }

    @Override
    public void addInsertTrigger(final Class clazz, final InsertTrigger insertTrigger) {
        execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                odb.addInsertTrigger(clazz, insertTrigger);
                return null;
            }
        });
    }

    @Override
    public void addSelectTrigger(final Class clazz, final SelectTrigger selectTrigger) {
        execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                odb.addSelectTrigger(clazz, selectTrigger);
                return null;
            }
        });

    }

    @Override
    public void addUpdateTrigger(final Class clazz, final UpdateTrigger updateTrigger) {
        execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                odb.addUpdateTrigger(clazz, updateTrigger);
                return null;
            }
        });

    }

    @Override
    public void close() {
        execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                odb.close();
                return null;
            }
        });
    }

    @Override
    public void commit() {
        execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                odb.commit();
                return null;
            }
        });
    }

    @Override
    public BigInteger count(final CriteriaQuery criteriaQuery) {
        return (BigInteger) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.count(criteriaQuery);
            }
        });
    }

    @Override
    public CriteriaQuery criteriaQuery(final Class clazz) {
        return (CriteriaQuery) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.criteriaQuery(clazz);
            }
        });
    }

    @Override
    public CriteriaQuery criteriaQuery(final Class clazz, final ICriterion criterion) {
        return (CriteriaQuery) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.criteriaQuery(clazz, criterion);
            }
        });
    }

    @Override
    public void defragmentTo(final String newFilename) {
        execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                odb.defragmentTo(newFilename);
                return null;
            }
        });
    }

    @Override
    public OID delete(final Object object) {
        return (OID) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.delete(object);
            }
        });
    }

    @Override
    public OID delecteCascade(final Object object) {
        return (OID) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.deleteCascade(object);
            }
        });
    }

    @Override
    public void deleteObjectWithId(final OID oid) {
        execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                odb.deleteObjectWithId(oid);
                return null;
            }
        });
    }

    @Override
    public void disconnect(final Object object) {
        execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                odb.disconnect(object);
                return null;
            }
        });
    }

    @Override
    public ClassRepresentation getClassRepresentation(final Class clazz) {
        return (ClassRepresentation) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.getClassRepresentation(clazz);
            }
        });
    }

    @Override
    public ClassRepresentation getClassRepresentation(final String fullClassname) {
        return (ClassRepresentation) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.getClassRepresentation(fullClassname);
            }
        });
    }

    @Override
    public ClassRepresentation getClassRepresentation(final String fullClassname, final boolean loadClass) {
        return (ClassRepresentation) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.getClassRepresentation(fullClassname, loadClass);
            }
        });
    }

    @Override
    public String getName() {
        return (String) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.getName();
            }
        });
    }

    @Override
    public Object getObjectFromId(final OID oid) {
        return execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.getObjectFromId(oid);
            }
        });
    }

    @Override
    public OID getObjectId(final Object object) {
        return (OID) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.getObjectId(object);
            }
        });
    }

    @Override
    public <T> Objects<T> getObjects(final Class clazz) {
        return (Objects<T>) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.getObjects(clazz);
            }
        });
    }

    @Override
    public <T> Objects<T> getObjects(final Class clazz, final boolean inMemory) {
        return (Objects<T>) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.getObjects(clazz, inMemory);
            }
        });
    }

    @Override
    public <T> Objects<T> getObjects(final Class clazz, final boolean inMemory, final int startIndex, final int endIndex) {
        return (Objects<T>) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.getObjects(clazz, inMemory, startIndex, endIndex);
            }
        });
    }

    @Override
    public <T> Objects<T> getObjects(final IQuery query) {
        return (Objects<T>) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.getObjects(query);
            }
        });
    }

    @Override
    public <T> Objects<T> getObjects(final IQuery query, final boolean inMemory) {
        return (Objects<T>) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.getObjects(query, inMemory);
            }
        });
    }

    @Override
    public <T> Objects<T> getObjects(final IQuery query, final boolean inMemory, final int startIndex, final int endIndex) {
        return (Objects<T>) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.getObjects(query, inMemory, startIndex, endIndex);
            }
        });
    }

    @Override
    public IRefactorManager getRefactorManager() {
        return (IRefactorManager) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.getRefactorManager();
            }
        });
    }

    @Override
    public Values getValues(final IValuesQuery query) {
        return (Values) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.getValues(query);
            }
        });
    }

    @Override
    public boolean isClosed() {
        return (Boolean) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.isClosed();
            }
        });
    }

    @Override
    public void rollback() {
        execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                odb.rollback();
                return null;
            }
        });
    }

    @Override
    public OID store(final Object object) {
        return (OID) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.store(object);
            }
        });
    }

    @Override
    public ExternalOID convertToExternalOID(final OID oid) {
        return (ExternalOID) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.ext().convertToExternalOID(oid);
            }
        });
    }

    @Override
    public TransactionId getCurrentTransactionId() {
        return (TransactionId) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.ext().getCurrentTransactionId();
            }
        });
    }

    @Override
    public DatabaseId getDatabaseId() {
        return (DatabaseId) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.ext().getDatabaseId();
            }
        });
    }

    @Override
    public long getObjectCreationDate(final OID oid) {
        return (Long) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.ext().getObjectCreationDate(oid);
            }
        });
    }

    @Override
    public ExternalOID getObjectExternalOID(final Object object) {
        return (ExternalOID) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.ext().getObjectExternalOID(object);
            }
        });
    }

    @Override
    public long getObjectUpdateDate(final OID oid, final boolean useCache) {
        return (Long) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.ext().getObjectUpdateDate(oid, useCache);
            }
        });
    }

    @Override
    public int getObjectVersion(final OID oid, final boolean useCache) {
        return (Integer) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.ext().getObjectVersion(oid, useCache);
            }
        });
    }

    @Override
    public OID replace(final OID oid, final Object object) {
        return (OID) execute(new NeoDatisCallback() {
            @Override
            public Object doInNeoDatis(ODB odb) throws RuntimeException {
                return odb.ext().replace(oid, object);
            }
        });
    }

}
