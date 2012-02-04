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
public interface NeoDatisOperations {

    Object execute(NeoDatisCallback callback) throws DataAccessException;

    //
    // ODB interface methods
    //

    void addDeleteTrigger(Class clazz, DeleteTrigger deleteTrigger);

    void addInsertTrigger(Class clazz, InsertTrigger insertTrigger);

    void addSelectTrigger(Class clazz, SelectTrigger selectTrigger);

    void addUpdateTrigger(Class clazz, UpdateTrigger updateTrigger);

    void close();

    // should this be possible here or on transaction manager
    void commit();

    BigInteger count(CriteriaQuery criteriaQuery);

    CriteriaQuery criteriaQuery(Class clazz);

    CriteriaQuery criteriaQuery(Class clazz, ICriterion criterion);

    void defragmentTo(String newFilename);

    OID delete(Object object);

    OID delecteCascade(Object object);

    void deleteObjectWithId(OID oid);

    void disconnect(Object object);

    ClassRepresentation getClassRepresentation(Class clazz);

    ClassRepresentation getClassRepresentation(String fullClassname);

    ClassRepresentation getClassRepresentation(String fullClassname, boolean loadClass);

    String getName();

    Object getObjectFromId(OID oid);

    OID getObjectId(Object object);

    <T> Objects<T> getObjects(Class clazz);

    <T> Objects<T> getObjects(Class clazz, boolean inMemory);

    <T> Objects<T> getObjects(Class clazz, boolean inMemory, int startIndex, int endIndex);

    <T> Objects<T> getObjects(IQuery query);

    <T> Objects<T> getObjects(IQuery query, boolean inMemory);

    <T> Objects<T> getObjects(IQuery query, boolean inMemory, int startIndex, int endIndex);

    IRefactorManager getRefactorManager();

    Values getValues(IValuesQuery query);

    boolean isClosed();

    // should this be possible here or on transaction manager
    void rollback();

    OID store(Object object);

    //
    // ODBExt interface methods
    //

    ExternalOID convertToExternalOID(OID oid);

    TransactionId getCurrentTransactionId();

    DatabaseId getDatabaseId();

    long getObjectCreationDate(OID oid);

    ExternalOID getObjectExternalOID(Object object);

    long getObjectUpdateDate(OID oid, boolean useCache);

    int getObjectVersion(OID oid, boolean useCache);

    OID replace(OID oid, Object object);
}
