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

import org.neodatis.odb.ODB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.InvalidIsolationLevelException;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.SmartTransactionObject;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SuppressWarnings("serial")
public class NeoDatisTransactionManager extends AbstractPlatformTransactionManager implements InitializingBean {

    private ODB odb;

    private final Logger logger = LoggerFactory.getLogger(NeoDatisTransactionManager.class);

    public NeoDatisTransactionManager() {
    }

    public NeoDatisTransactionManager(ODB odb) {
        this.odb = odb;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (getOdb() == null) {
            throw new IllegalArgumentException("ODB is required");
        }
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition transactionDefinition) throws TransactionException {
        if (transactionDefinition.getIsolationLevel() != TransactionDefinition.ISOLATION_DEFAULT) {
            throw new InvalidIsolationLevelException("NeoDatis does not support an isolation level concept.");
        }

        ODB odb = null;

        try {
            NeoDatisTransactionObject transactionObject = (NeoDatisTransactionObject) transaction;
            if (transactionObject.getODBHolder() == null) {
                odb = getOdb();
                logger.debug("Using given ODB [{}] for the current thread transaction.", odb);
                transactionObject.setODBHolder(new ODBHolder(odb));
            }

            ODBHolder odbHolder = transactionObject.getODBHolder();

            odbHolder.setSynchronizedWithTransaction(true);

            // start transaction
            // no-op
            // register timeout
            if (transactionDefinition.getTimeout() != TransactionDefinition.TIMEOUT_DEFAULT) {
                transactionObject.getODBHolder().setTimeoutInSeconds(transactionDefinition.getTimeout());
            }

            //Bind session holder to the thread
            TransactionSynchronizationManager.bindResource(getOdb(), odbHolder);

        } catch (Exception e) {
            throw new CannotCreateTransactionException("Can not create an NeoDatis transaction.", e);
        }

    }

    @Override
    protected Object doSuspend(Object transaction) throws TransactionException {
        NeoDatisTransactionObject transactionObject = (NeoDatisTransactionObject) transaction;
        transactionObject.setODBHolder(null);
        ODBHolder odbHolder = (ODBHolder) TransactionSynchronizationManager.unbindResource(getOdb());
        return new SuspendedResourcesHolder(odbHolder);
    }

    @Override
    protected void doResume(Object transaction, Object suspendedResources) throws TransactionException {
        SuspendedResourcesHolder suspendedResourcesHolder = (SuspendedResourcesHolder) suspendedResources;
        if (TransactionSynchronizationManager.hasResource(getOdb())) {
            TransactionSynchronizationManager.unbindResource(getOdb());
        }
        TransactionSynchronizationManager.bindResource(getOdb(), suspendedResourcesHolder.getOdbHolder());
    }

    @Override
    protected void doCommit(DefaultTransactionStatus transactionStatus) throws TransactionException {
        NeoDatisTransactionObject transactionObject = (NeoDatisTransactionObject) transactionStatus.getTransaction();
        logger.debug("Committing NeoDatis transaction on ODB [{}]", transactionObject.getODBHolder().getOdb());
        try {
            transactionObject.getODBHolder().getOdb().commit();
        } catch (Exception e) {
            throw new TransactionSystemException("Could not commit NeoDatis transaction.", e);
        }
    }

    @Override
    protected Object doGetTransaction() throws TransactionException {
        NeoDatisTransactionObject transactionObject = new NeoDatisTransactionObject();
        ODBHolder odbHolder = (ODBHolder) TransactionSynchronizationManager.getResource(getOdb());
        transactionObject.setODBHolder(odbHolder);
        return transactionObject;
    }

    @Override
    protected boolean isExistingTransaction(Object transaction) throws TransactionException {
        return ((NeoDatisTransactionObject) transaction).hasTransaction();
    }

    @Override
    protected void doRollback(DefaultTransactionStatus transactionStatus) throws TransactionException {
        NeoDatisTransactionObject transactionObject = (NeoDatisTransactionObject) transactionStatus.getTransaction();
        logger.debug("Rolling back NeoDatis transaction on ODB [{}]", transactionObject.getODBHolder().getOdb());
        try {
            transactionObject.getODBHolder().getOdb().rollback();
        } catch (Exception e) {
            throw new TransactionSystemException("Could not rollback NeoDatis transaction.", e);
        }
    }

    @Override
    protected void doSetRollbackOnly(DefaultTransactionStatus status) throws TransactionException {
        NeoDatisTransactionObject transactionObject = (NeoDatisTransactionObject) status.getTransaction();
        logger.debug("Setting NeoDatis transaction on ODB [{}] to rollback only.", transactionObject.getODBHolder()
                .getOdb());
        transactionObject.setRollbackOnly();
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        NeoDatisTransactionObject transactionObject = (NeoDatisTransactionObject) transaction;
        //Remove SessionHolder from the Thread.
        TransactionSynchronizationManager.unbindResource(getOdb());
        transactionObject.getODBHolder().clear();
    }

    private static class NeoDatisTransactionObject implements SmartTransactionObject {

        private ODBHolder odbHolder;

        public void setODBHolder(ODBHolder odbHolder) {
            this.odbHolder = odbHolder;
        }

        public ODBHolder getODBHolder() {
            return odbHolder;
        }

        @Override
        public void flush() {
            // no-op
        }

        public void setRollbackOnly() {
            this.odbHolder.setRollbackOnly();
        }

        @Override
        public boolean isRollbackOnly() {
            return odbHolder.isRollbackOnly();
        }

        public boolean hasTransaction() {
            return (this.odbHolder != null);
        }

    }

    private static class SuspendedResourcesHolder {

        private final ODBHolder odbHolder;

        public SuspendedResourcesHolder(ODBHolder odbHolder) {
            this.odbHolder = odbHolder;
        }

        public ODBHolder getOdbHolder() {
            return odbHolder;
        }

    }

    public ODB getOdb() {
        return odb;
    }

    public void setOdb(ODB odb) {
        this.odb = odb;
    }

}
