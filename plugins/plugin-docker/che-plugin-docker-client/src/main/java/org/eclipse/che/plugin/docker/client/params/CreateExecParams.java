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
package org.eclipse.che.plugin.docker.client.params;

/**
 * Arguments holder for {@link org.eclipse.che.plugin.docker.client.DockerConnector#createExec(CreateExecParams)}.
 *
 * @author Mykola Morhun
 */
public class CreateExecParams {

    private String   container;
    private Boolean  detach;
    private String[] cmd;

    /**
     * @param container
     *         id or name of container
     */
    public CreateExecParams withContainer(String container) {
        this.container = container;
        return this;
    }

    /**
     * @param detach
     *         is stdout & stderr detached
     */
    public CreateExecParams withDetach(boolean detach) {
        this.detach = detach;
        return this;
    }

    /**
     * @param cmd
     *         command to run specified as a string or an array of strings
     */
    public CreateExecParams withCmd(String[] cmd) {
        this.cmd = cmd;
        return this;
    }

    public String getContainer() {
        return container;
    }

    public Boolean isDetach() {
        return detach;
    }

    public String[] getCmd() {
        return cmd;
    }

}
