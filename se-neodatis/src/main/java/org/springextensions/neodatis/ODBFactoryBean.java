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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.FactoryBeanNotInitializedException;

/**
 * 
 * @author Joerg Bellmann
 *
 */
public class ODBFactoryBean implements FactoryBean<ODB> {

    private static final Logger LOG = LoggerFactory.getLogger(ODBFactoryBean.class);

    private ODB odb;

    private String filename;

    @Override
    public ODB getObject() throws Exception {
        if (odb == null) {
            throw new FactoryBeanNotInitializedException("ODB not opened.");
        }
        return odb;
    }

    @Override
    public Class<ODB> getObjectType() {
        return ODB.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @PostConstruct
    public void initialize() {
        if (filename != null) {
            odb = openOdb();
        } else {
            throw new IllegalArgumentException("Filename is mandantory.");
        }
        LOG.info("ODB opened.");
    }

    protected ODB openOdb() {
        return ODBFactory.open(filename);
    }

    @PreDestroy
    public void destroy() {
        if(!odb.isClosed()){
        	odb.close();
        	LOG.info("Closing ODB");
        }
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
