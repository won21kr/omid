/**
 * Copyright 2011-2016 Yahoo Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yahoo.omid.tso;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.hadoop.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * For yjava_daemon
 */
public class TsoServerDaemon implements Daemon {
    private static final Logger LOG = LoggerFactory.getLogger(TsoServerDaemon.class);
    private TSOServer tsoServer;

    @Override
    public void init(DaemonContext daemonContext) throws Exception {
        final String[] arguments = daemonContext.getArguments();
        LOG.info("Starting TSOServer, args: {}", StringUtils.join(" ", Arrays.asList(arguments)));
        try {
            TSOServerCommandLineConfig config = TSOServerCommandLineConfig.parseConfig(arguments);
            tsoServer = TSOServer.getInitializedTsoServer(config);
        } catch (Exception e) {
            LOG.error("Error creating TSOServer instance", e);
        }
    }

    @Override
    public void start() throws Exception {
        tsoServer.startAndWait();
    }

    @Override
    public void stop() throws Exception {
        tsoServer.stopAndWait();
    }

    @Override
    public void destroy() {

    }
}
