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

public class NeoDatisTransactionManagerTest {

    @Test
    public void testTransactionCommit() throws Exception {
        final ODB odb = Mockito.mock(ODB.class);
        PlatformTransactionManager tm = new NeoDatisTransactionManager(odb);
        TransactionTemplate tmpl = new TransactionTemplate(tm);

        Assert.assertFalse("Should not have a resource", TransactionSynchronizationManager.hasResource(odb));
        Assert.assertFalse("There should no active synchronizations",
                TransactionSynchronizationManager.isSynchronizationActive());

        tmpl.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Assert.assertTrue(TransactionSynchronizationManager.hasResource(odb));
                NeoDatisTemplate neoDatisTemplate = new NeoDatisTemplate(odb);
                neoDatisTemplate.store(null);
            }
        });

        Assert.assertFalse("Should not have a resource", TransactionSynchronizationManager.hasResource(odb));
        Assert.assertFalse("There should no active synchronizations",
                TransactionSynchronizationManager.isSynchronizationActive());

        Mockito.verify(odb, Mockito.times(1)).commit();

    }

    @Test
    public void testTransactionRollback() {
        final ODB odb = Mockito.mock(ODB.class);
        Mockito.when(odb.store(Mockito.isNull())).thenThrow(new RuntimeException());
        PlatformTransactionManager tm = new NeoDatisTransactionManager(odb);
        TransactionTemplate tmpl = new TransactionTemplate(tm);

        Assert.assertFalse("Should not have a resource", TransactionSynchronizationManager.hasResource(odb));
        Assert.assertFalse("There should no active synchronizations",
                TransactionSynchronizationManager.isSynchronizationActive());

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
        Assert.assertFalse("There should no active synchronizations",
                TransactionSynchronizationManager.isSynchronizationActive());

        Mockito.verify(odb, Mockito.times(1)).rollback();
    }

    @Test
    public void testTransactionRollbackOnly() {
        final ODB odb = Mockito.mock(ODB.class);
        Mockito.when(odb.store(Mockito.isNull())).thenThrow(new RuntimeException());
        PlatformTransactionManager tm = new NeoDatisTransactionManager(odb);
        TransactionTemplate tmpl = new TransactionTemplate(tm);

        Assert.assertFalse("Should not have a resource", TransactionSynchronizationManager.hasResource(odb));
        Assert.assertFalse("There should no active synchronizations",
                TransactionSynchronizationManager.isSynchronizationActive());

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
        Assert.assertFalse("There should no active synchronizations",
                TransactionSynchronizationManager.isSynchronizationActive());

        Mockito.verify(odb, Mockito.times(1)).rollback();
    }

    @Test
    public void testWrongIsolationLevel() {
        final ODB odb = Mockito.mock(ODB.class);
        Mockito.when(odb.store(Mockito.isNull())).thenThrow(new RuntimeException());
        PlatformTransactionManager tm = new NeoDatisTransactionManager(odb);
        TransactionTemplate tmpl = new TransactionTemplate(tm);

        Assert.assertFalse("Should not have a resource", TransactionSynchronizationManager.hasResource(odb));
        Assert.assertFalse("There should no active synchronizations",
                TransactionSynchronizationManager.isSynchronizationActive());

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
        Assert.assertFalse("There should no active synchronizations",
                TransactionSynchronizationManager.isSynchronizationActive());

    }

}
