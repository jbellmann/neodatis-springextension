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
import org.junit.Test;
import org.mockito.Mockito;
import org.neodatis.odb.ODB;
import org.springframework.transaction.InvalidIsolationLevelException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 
 * @author Joerg Bellmann
 * 
 */
public class NeoDatisTransactionManagerTest {

    @Test
    public void testTransactionCommit() throws Exception {
        final ODB odb = Mockito.mock(ODB.class);
        PlatformTransactionManager tm = new NeoDatisTransactionManager(odb);
        TransactionTemplate tmpl = new TransactionTemplate(tm);

        Assert.assertFalse("Should not have a resource", TransactionSynchronizationManager.hasResource(odb));
        Assert.assertFalse("There should no active synchronizations", TransactionSynchronizationManager.isSynchronizationActive());

        tmpl.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Assert.assertTrue(TransactionSynchronizationManager.hasResource(odb));
                NeoDatisTemplate neoDatisTemplate = new NeoDatisTemplate(odb);
                neoDatisTemplate.store(null);
            }
        });

        Assert.assertFalse("Should not have a resource", TransactionSynchronizationManager.hasResource(odb));
        Assert.assertFalse("There should no active synchronizations", TransactionSynchronizationManager.isSynchronizationActive());

        Mockito.verify(odb, Mockito.times(1)).commit();

    }

    @Test
    public void testTransactionRollback() {
        final ODB odb = Mockito.mock(ODB.class);
        Mockito.when(odb.store(Mockito.isNull())).thenThrow(new RuntimeException());
        PlatformTransactionManager tm = new NeoDatisTransactionManager(odb);
        TransactionTemplate tmpl = new TransactionTemplate(tm);

        Assert.assertFalse("Should not have a resource", TransactionSynchronizationManager.hasResource(odb));
        Assert.assertFalse("There should no active synchronizations", TransactionSynchronizationManager.isSynchronizationActive());

        try {
            tmpl.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    Assert.assertTrue(TransactionSynchronizationManager.hasResource(odb));
                    NeoDatisTemplate neoDatisTemplate = new NeoDatisTemplate(odb);
                    neoDatisTemplate.store(null);
                }
            });
        } catch (RuntimeException e) {
            // is ok
        }

        Assert.assertFalse("Should not have a resource", TransactionSynchronizationManager.hasResource(odb));
        Assert.assertFalse("There should no active synchronizations", TransactionSynchronizationManager.isSynchronizationActive());

        Mockito.verify(odb, Mockito.times(1)).rollback();
    }

    @Test
    public void testTransactionRollbackOnly() {
        final ODB odb = Mockito.mock(ODB.class);
        Mockito.when(odb.store(Mockito.isNull())).thenThrow(new RuntimeException());
        PlatformTransactionManager tm = new NeoDatisTransactionManager(odb);
        TransactionTemplate tmpl = new TransactionTemplate(tm);

        Assert.assertFalse("Should not have a resource", TransactionSynchronizationManager.hasResource(odb));
        Assert.assertFalse("There should no active synchronizations", TransactionSynchronizationManager.isSynchronizationActive());

        try {
            tmpl.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    Assert.assertTrue(TransactionSynchronizationManager.hasResource(odb));
                    NeoDatisTemplate neoDatisTemplate = new NeoDatisTemplate(odb);
                    neoDatisTemplate.store(null);
                    transactionStatus.setRollbackOnly();
                }
            });
        } catch (RuntimeException e) {
            // is ok
        }

        Assert.assertFalse("Should not have a resource", TransactionSynchronizationManager.hasResource(odb));
        Assert.assertFalse("There should no active synchronizations", TransactionSynchronizationManager.isSynchronizationActive());

        Mockito.verify(odb, Mockito.times(1)).rollback();
    }

    @Test
    public void testWrongIsolationLevel() {
        final ODB odb = Mockito.mock(ODB.class);
        Mockito.when(odb.store(Mockito.isNull())).thenThrow(new RuntimeException());
        PlatformTransactionManager tm = new NeoDatisTransactionManager(odb);
        TransactionTemplate tmpl = new TransactionTemplate(tm);

        Assert.assertFalse("Should not have a resource", TransactionSynchronizationManager.hasResource(odb));
        Assert.assertFalse("There should no active synchronizations", TransactionSynchronizationManager.isSynchronizationActive());

        tmpl.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        try {
            tmpl.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    Assert.assertTrue(TransactionSynchronizationManager.hasResource(odb));
                    NeoDatisTemplate neoDatisTemplate = new NeoDatisTemplate(odb);
                    neoDatisTemplate.store(null);
                    transactionStatus.setRollbackOnly();
                }
            });
            Assert.fail("Should throw an exception.");
        } catch (InvalidIsolationLevelException e) {
            // is ok
        }

        Assert.assertFalse("Should not have a resource", TransactionSynchronizationManager.hasResource(odb));
        Assert.assertFalse("There should no active synchronizations", TransactionSynchronizationManager.isSynchronizationActive());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoArgumentConstructor() throws Exception {
        NeoDatisTransactionManager txManager = new NeoDatisTransactionManager();
        txManager.afterPropertiesSet();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullArgumentConstructor() throws Exception {
        NeoDatisTransactionManager txManager = new NeoDatisTransactionManager(null);
        txManager.afterPropertiesSet();
    }

    @Test
    public void testAfterPropertiesSetWithOdb() throws Exception {
        NeoDatisTransactionManager txManager = new NeoDatisTransactionManager(Mockito.mock(ODB.class));
        txManager.afterPropertiesSet();
    }

}
