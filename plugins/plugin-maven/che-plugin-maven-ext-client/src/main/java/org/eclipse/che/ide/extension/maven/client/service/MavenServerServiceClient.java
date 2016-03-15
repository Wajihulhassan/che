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
package org.eclipse.che.ide.extension.maven.client.service;

import com.google.inject.ImplementedBy;

import org.eclipse.che.api.promises.client.Promise;

/**
 * Client for Maven Server API.
 *
 * @author Valeriy Svydenko
 */
@ImplementedBy(MavenServerServiceClientImpl.class)
public interface MavenServerServiceClient {

    /**
     * Returns effective pom.
     *
     * @param projectPath
     *         path to current project
     * @return content of the effective pom
     */
    Promise<String> getEffectivePom(String projectPath);
}
