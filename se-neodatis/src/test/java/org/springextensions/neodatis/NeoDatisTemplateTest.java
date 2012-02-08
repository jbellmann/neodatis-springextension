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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBExt;
import org.neodatis.odb.OID;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.layers.layer3.IRefactorManager;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.IValuesQuery;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.trigger.DeleteTrigger;
import org.neodatis.odb.core.trigger.InsertTrigger;
import org.neodatis.odb.core.trigger.SelectTrigger;
import org.neodatis.odb.core.trigger.UpdateTrigger;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

/**
 *
 * @author Joerg Bellmann
 *
 */
public class NeoDatisTemplateTest {

    private ODB odb = Mockito.mock(ODB.class);

    private NeoDatisTemplate neoDatisTemplate;

    @Before
    public void setUp() {
        neoDatisTemplate = new NeoDatisTemplate(odb);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullOdb() {
        new NeoDatisTemplate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAfterPropertiesSet() {
        NeoDatisTemplate template = new NeoDatisTemplate();
        template.setOdb(null);
        template.afterPropertiesSet();
    }

    @Test
    public void testAddDeleteTrigger() {
        DeleteTrigger deleteTrigger = Mockito.mock(DeleteTrigger.class);
        neoDatisTemplate.addDeleteTrigger(Object.class, deleteTrigger);
        Mockito.verify(odb).addDeleteTrigger(Object.class, deleteTrigger);
    }

    @Test
    public void testAddInsertTrigger() {
        InsertTrigger insertTrigger = Mockito.mock(InsertTrigger.class);
        neoDatisTemplate.addInsertTrigger(Object.class, insertTrigger);
        Mockito.verify(odb).addInsertTrigger(Object.class, insertTrigger);
    }

    @Test
    public void testAddSelectTrigger() {
        SelectTrigger selectTrigger = Mockito.mock(SelectTrigger.class);
        neoDatisTemplate.addSelectTrigger(Object.class, selectTrigger);
        Mockito.verify(odb).addSelectTrigger(Object.class, selectTrigger);
    }

    @Test
    public void testAddUpdateTrigger() {
        UpdateTrigger updateTrigger = Mockito.mock(UpdateTrigger.class);
        neoDatisTemplate.addUpdateTrigger(Object.class, updateTrigger);
        Mockito.verify(odb).addUpdateTrigger(Object.class, updateTrigger);
    }

    @Test
    public void testClose() {
        neoDatisTemplate.close();
        Mockito.verify(odb).close();
    }

    @Test
    public void testCommit() {
        neoDatisTemplate.commit();
        Mockito.verify(odb).commit();
    }

    @Test
    public void testCount() {
        CriteriaQuery criteriaQuery = Mockito.mock(CriteriaQuery.class);
        neoDatisTemplate.count(criteriaQuery);
        Mockito.verify(odb).count(criteriaQuery);
    }

    @Test
    public void testCriteriaQuery() {
        neoDatisTemplate.criteriaQuery(Object.class);
        Mockito.verify(odb).criteriaQuery(Object.class);
    }

    @Test
    public void testCriterionQuery() {
        ICriterion criterion = Mockito.mock(ICriterion.class);
        neoDatisTemplate.criteriaQuery(Object.class, criterion);
        Mockito.verify(odb).criteriaQuery(Object.class, criterion);
    }

    @Test
    public void testDefragmentTo() {
        neoDatisTemplate.defragmentTo("TestFileName");
        Mockito.verify(odb).defragmentTo(Mockito.anyString());
    }

    @Test
    public void testDelete() {
        neoDatisTemplate.delete(Mockito.mock(Object.class));
        Mockito.verify(odb).delete(Mockito.any());
    }

    @Test
    public void testDeleteCascade() {
        neoDatisTemplate.delecteCascade(Mockito.mock(Object.class));
        Mockito.verify(odb).deleteCascade(Mockito.any());
    }

    @Test
    public void testDeleteObjectWithId() {
        OID oid = Mockito.mock(OID.class);
        neoDatisTemplate.deleteObjectWithId(oid);
        Mockito.verify(odb).deleteObjectWithId(oid);
    }

    @Test
    public void testDisconnect() {
        neoDatisTemplate.disconnect(Mockito.mock(Object.class));
        Mockito.verify(odb).disconnect(Mockito.any());
    }

    @Test
    public void testGetClassRepresentation() {
        neoDatisTemplate.getClassRepresentation(Object.class);
        Mockito.verify(odb).getClassRepresentation(Object.class);
    }

    @Test
    public void testGetClassRepresentationForClassname() {
        neoDatisTemplate.getClassRepresentation(String.class.getName());
        Mockito.verify(odb).getClassRepresentation(Mockito.anyString());
    }

    @Test
    public void testGetClassRepresentationForClassnameWithLoad() {
        neoDatisTemplate.getClassRepresentation(String.class.getName(), true);
        Mockito.verify(odb).getClassRepresentation(String.class.getName(), true);
    }

    @Test
    public void testGetName() {
        neoDatisTemplate.getName();
        Mockito.verify(odb).getName();
    }

    @Test
    public void testGetObjectFromId() {
        neoDatisTemplate.getObjectFromId(Mockito.mock(OID.class));
        Mockito.verify(odb).getObjectFromId(Mockito.any(OID.class));
    }

    @Test
    public void testGetObjectId() {
        neoDatisTemplate.getObjectId(Mockito.mock(Object.class));
        Mockito.verify(odb).getObjectId(Mockito.any(Object.class));
    }

    @Test
    public void testGetObjects() {
        Objects<Object> mockResult = Mockito.mock(Objects.class);
        Mockito.when(odb.getObjects(Object.class)).thenReturn(mockResult);
        Objects<Object> result = neoDatisTemplate.getObjects(Object.class);
        Mockito.verify(odb).getObjects(Object.class);
        Assert.assertNotNull(result);
        Assert.assertSame(result, mockResult);
    }

    @Test
    public void testGetObjectsFromMemory() {
        Objects<Object> mockResult = Mockito.mock(Objects.class);
        Mockito.when(odb.getObjects(Object.class, true)).thenReturn(mockResult);
        Objects<Object> result = neoDatisTemplate.getObjects(Object.class, true);
        Mockito.verify(odb).getObjects(Object.class, true);
        Assert.assertNotNull(result);
        Assert.assertSame(result, mockResult);
    }

    @Test
    public void testGetObjectsFromMemoryIndexed() {
        Objects<Object> mockResult = Mockito.mock(Objects.class);
        Mockito.when(odb.getObjects(Object.class, true, 0, 12)).thenReturn(mockResult);
        Objects<Object> result = neoDatisTemplate.getObjects(Object.class, true, 0, 12);
        Mockito.verify(odb).getObjects(Object.class, true, 0, 12);
        Assert.assertNotNull(result);
        Assert.assertSame(result, mockResult);
    }

    @Test
    public void testGetObjectsWithIQuery() {
        IQuery query = Mockito.mock(IQuery.class);
        neoDatisTemplate.getObjects(query);
        Mockito.verify(odb).getObjects(query);
    }

    @Test
    public void testGetObjectsWithIQueryFromMemory() {
        IQuery query = Mockito.mock(IQuery.class);
        neoDatisTemplate.getObjects(query, true);
        Mockito.verify(odb).getObjects(query, true);
    }

    @Test
    public void testGetObjectsWithIQueryFromMemoryIndexed() {
        IQuery query = Mockito.mock(IQuery.class);
        neoDatisTemplate.getObjects(query, true, 1, 13);
        Mockito.verify(odb).getObjects(query, true, 1, 13);
    }

    @Test
    public void testGetRefactorManager() {
        IRefactorManager refactorManager = Mockito.mock(IRefactorManager.class);
        Mockito.when(odb.getRefactorManager()).thenReturn(refactorManager);
        IRefactorManager result = neoDatisTemplate.getRefactorManager();
        Mockito.verify(odb).getRefactorManager();
        Assert.assertSame(refactorManager, result);
    }

    @Test
    public void testGetValues() {
        IValuesQuery iValuesQuery = Mockito.mock(IValuesQuery.class);
        neoDatisTemplate.getValues(iValuesQuery);
        Mockito.verify(odb).getValues(iValuesQuery);
    }

    @Test
    public void testIsClosed() {
        neoDatisTemplate.isClosed();
        Mockito.verify(odb).isClosed();
    }

    @Test
    public void testRollback() {
        neoDatisTemplate.rollback();
        Mockito.verify(odb).rollback();
    }

    @Test
    public void testStore() {
        neoDatisTemplate.store(Mockito.mock(Object.class));
        Mockito.verify(odb).store(Mockito.any());
    }

    @Test
    public void testConvertToExternalOID() {
        OID oid = Mockito.mock(OID.class);
        ODBExt ext = Mockito.mock(ODBExt.class);
        Mockito.when(odb.ext()).thenReturn(ext);
        neoDatisTemplate.convertToExternalOID(oid);
        Mockito.verify(odb).ext();
        Mockito.verify(ext).convertToExternalOID(oid);
    }

    @Test
    public void testGetCurrentTransactionId() {
        ODBExt ext = Mockito.mock(ODBExt.class);
        Mockito.when(odb.ext()).thenReturn(ext);
        neoDatisTemplate.getCurrentTransactionId();
        Mockito.verify(odb).ext();
        Mockito.verify(ext).getCurrentTransactionId();
    }

    @Test
    public void testGetDatabaseId() {
        ODBExt ext = Mockito.mock(ODBExt.class);
        Mockito.when(odb.ext()).thenReturn(ext);
        neoDatisTemplate.getDatabaseId();
        Mockito.verify(odb).ext();
        Mockito.verify(ext).getDatabaseId();
    }

    @Test
    public void testGetObjectCreationDate() {
        OID oid = Mockito.mock(OID.class);
        ODBExt ext = Mockito.mock(ODBExt.class);
        Mockito.when(odb.ext()).thenReturn(ext);
        neoDatisTemplate.getObjectCreationDate(oid);
        Mockito.verify(odb).ext();
        Mockito.verify(ext).getObjectCreationDate(oid);
    }

    @Test
    public void testGetObjectExternalOid() {
        ODBExt ext = Mockito.mock(ODBExt.class);
        Mockito.when(odb.ext()).thenReturn(ext);
        neoDatisTemplate.getObjectExternalOID(Mockito.mock(Object.class));
        Mockito.verify(odb).ext();
        Mockito.verify(ext).getObjectExternalOID(Mockito.any());
    }

    @Test
    public void testGetObjectUpdateDate() {
        OID oid = Mockito.mock(OID.class);
        ODBExt ext = Mockito.mock(ODBExt.class);
        Mockito.when(odb.ext()).thenReturn(ext);
        neoDatisTemplate.getObjectUpdateDate(oid, true);
        Mockito.verify(odb).ext();
        Mockito.verify(ext).getObjectUpdateDate(oid, true);
    }

    @Test
    public void testGetObjectVersion() {
        OID oid = Mockito.mock(OID.class);
        ODBExt ext = Mockito.mock(ODBExt.class);
        Mockito.when(odb.ext()).thenReturn(ext);
        neoDatisTemplate.getObjectVersion(oid, true);
        Mockito.verify(odb).ext();
        Mockito.verify(ext).getObjectVersion(oid, true);
    }

    @Test
    public void testReplace() {
        OID oid = Mockito.mock(OID.class);
        Object object = Mockito.mock(Object.class);
        ODBExt ext = Mockito.mock(ODBExt.class);
        Mockito.when(odb.ext()).thenReturn(ext);
        neoDatisTemplate.replace(oid, object);
        Mockito.verify(odb).ext();
        Mockito.verify(ext).replace(oid, object);
    }
}
