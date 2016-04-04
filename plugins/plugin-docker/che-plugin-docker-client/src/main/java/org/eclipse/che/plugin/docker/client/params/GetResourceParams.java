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
 * Arguments holder for {@link org.eclipse.che.plugin.docker.client.DockerConnector#getResource(GetResourceParams)}.
 *
 * @author Mykola Morhun
 */
public class GetResourceParams {

    private String container;
    private String sourcePath;

    /**
     * @param container
     *         container id
     */
    public GetResourceParams withContainer(String container) {
        this.container = container;
        return this;
    }

    /**
     * @param sourcePath
     *         path to file or directory inside specified container
     */
    public GetResourceParams withSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
        return this;
    }

    public String getContainer() {
        return container;
    }

    public String getSourcePath() {
        return sourcePath;
    }

}
