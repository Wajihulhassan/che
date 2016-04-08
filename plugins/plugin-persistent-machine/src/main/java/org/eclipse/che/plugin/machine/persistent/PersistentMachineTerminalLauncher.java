/*******************************************************************************
 * Copyright (c) 2012-2016 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.plugin.machine.persistent;

import org.eclipse.che.api.core.ConflictException;
import org.eclipse.che.api.core.util.ListLineConsumer;
import org.eclipse.che.api.core.util.NoCloseLineConsumer;
import org.eclipse.che.api.machine.server.exception.MachineException;
import org.eclipse.che.api.machine.server.model.impl.CommandImpl;
import org.eclipse.che.api.machine.server.spi.Instance;
import org.eclipse.che.api.machine.server.spi.InstanceProcess;
import org.eclipse.che.api.machine.server.terminal.MachineSpecificTerminalLauncher;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;

import static com.google.common.base.MoreObjects.firstNonNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Launch websocket terminal in persistent machines.
 *
 * author Alexander Garagatyi
 */
public class PersistentMachineTerminalLauncher implements MachineSpecificTerminalLauncher {
    private static final Logger LOG = getLogger(PersistentMachineTerminalLauncher.class);

    public static final String TERMINAL_LAUNCH_COMMAND_PROPERTY = "machine.persistent.server.terminal.run_command";
    public static final String TERMINAL_LOCATION_PROPERTY       = "machine.persistent.server.terminal.location";

    private final String                             runTerminalCommand;
    private final String                             terminalLocation;
    private final WebsocketTerminalFilesPathProvider archivePathProvider;

    @Inject
    public PersistentMachineTerminalLauncher(@Named(TERMINAL_LAUNCH_COMMAND_PROPERTY) String runTerminalCommand,
                                             @Named(TERMINAL_LOCATION_PROPERTY) String terminalLocation,
                                             WebsocketTerminalFilesPathProvider terminalPathProvider) {
        this.runTerminalCommand = runTerminalCommand;
        this.terminalLocation = terminalLocation;
        this.archivePathProvider = terminalPathProvider;
    }

    @Override
    public String getMachineType() {
        return "persistent";
    }

    @Override
    public void launchTerminal(Instance machine) throws MachineException {
        try {
            InstanceProcess checkTerminalAlive = machine.createProcess(
                    new CommandImpl("check if che websocket terminal is running",
                                    "ps ax | grep '" + runTerminalCommand + "' | grep -v 'grep " + runTerminalCommand +
                                    "' && echo 'found' || echo 'not found'",
                                    null),
                    null);
            ListLineConsumer lineConsumer = new ListLineConsumer();
            checkTerminalAlive.start(lineConsumer);
            String checkAliveText = lineConsumer.getText();

            if ("not found".equals(checkAliveText)) {
                // todo check existing version of terminal, do not copy if it is up to date
                machine.copy(archivePathProvider.getPath(firstNonNull(machine.getConfig().getArchitecture(), "linux_amd64")),
                             terminalLocation);

                InstanceProcess startTerminal = machine.createProcess(new CommandImpl("websocket terminal",
                                                                                      "/bin/bash -c " + runTerminalCommand,
                                                                                      null),
                                                                      null);

                startTerminal.start((NoCloseLineConsumer)LOG::info);// todo
            } else if (!"found".equals(checkAliveText)) {
                throw new MachineException("Websocket terminal state check failed. Error: " + checkAliveText);
            }
        } catch (ConflictException e) {
            throw new MachineException("Internal server error occurs on terminal launching.");
        }
    }
}
