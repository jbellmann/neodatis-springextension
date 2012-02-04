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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;

public abstract class NeoDatisAccessor implements InitializingBean {

    private ODB odb;

    public void setOdb(ODB odb) {
        this.odb = odb;
    }

    public ODB getOdb() {
        return this.odb;
    }

    @Override
    public void afterPropertiesSet() {
        if (this.odb == null) {
            throw new IllegalArgumentException("ODB is required.");
        }
    }

    public DataAccessException convertNeoDatisAccessException(Exception e) {
        return NeoDatisUtils.translateException(e);
    }

}
