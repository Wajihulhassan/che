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
package org.eclipse.che.plugin.docker.machine;

import org.eclipse.che.api.machine.server.exception.MachineException;
import org.eclipse.che.api.machine.server.spi.Instance;
import org.eclipse.che.api.machine.server.terminal.MachineSpecificTerminalLauncher;
import org.eclipse.che.plugin.docker.client.DockerConnector;
import org.eclipse.che.plugin.docker.client.Exec;
import org.eclipse.che.plugin.docker.client.LogMessage;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

/**
 * Starts websocket terminal in the machine after container start
 *
 * @author Alexander Garagatyi
 */
public class DockerMachineTerminalLauncher implements MachineSpecificTerminalLauncher {
    public static final String START_TERMINAL_COMMAND = "machine.docker.server.terminal.run_command";

    private final DockerConnector docker;
    private final String          terminalStartCommand;

    @Inject
    public DockerMachineTerminalLauncher(DockerConnector docker,
                                         @Named(START_TERMINAL_COMMAND) String terminalStartCommand) {
        this.docker = docker;
        this.terminalStartCommand = terminalStartCommand;
    }

    @Override
    public String getMachineType() {
        return "docker";
    }

    @Override
    public void launchTerminal(Instance machine) throws MachineException {
        if (DockerInstance.class.isAssignableFrom(machine.getClass())) {
            try {
                final String container = ((DockerInstance)machine).getContainer();

                final Exec exec = docker.createExec(container, true, "/bin/bash", "-c", terminalStartCommand);

                docker.startExec(exec.getId(), logMessage -> {
                    if (logMessage.getType() == LogMessage.Type.STDERR) {
                        try {
                            machine.getLogger().writeLine("Terminal error. %s" + logMessage.getContent());
                        } catch (IOException ignore) {
                        }
                    }
                });
            } catch (IOException e) {
                throw new MachineException(e.getLocalizedMessage(), e);
            }
        } else {
            throw new MachineException("Docker terminal launcher was used to launch terminal in non-docker machine.");
        }
    }
}
