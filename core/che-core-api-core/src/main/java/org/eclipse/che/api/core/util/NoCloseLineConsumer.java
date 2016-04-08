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
package org.eclipse.che.api.core.util;

import java.io.IOException;

/**
 * {@link LineConsumer} that should not be closed.
 *
 * author Alexander Garagatyi
 */
public interface NoCloseLineConsumer extends LineConsumer {
    @Override
    default void close() throws IOException {}
}
