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
package org.eclipse.che.api.project.server;

import org.eclipse.che.api.core.ForbiddenException;
import org.eclipse.che.api.core.ServerException;
import org.eclipse.che.api.vfs.Path;
import org.eclipse.che.api.vfs.VirtualFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper for VirtualFile.
 *
 * @author andrew00x
 */
public abstract class VirtualFileEntry {

    protected String project;
    private       VirtualFile virtualFile;
    protected Map<String, String> attributes;

    public VirtualFileEntry(VirtualFile virtualFile, String project) {
        this.virtualFile = virtualFile;
        this.attributes = new HashMap<>();
        this.project = project;
    }

    /**
     *
     * @return last modification date
     */
    public long getModified() {
        return virtualFile.getLastModificationDate();
    }


    /**
     * Tests whether this item is a regular file.
     *
     * @see org.eclipse.che.api.vfs.server.VirtualFile#isFile()
     */
    public boolean isFile() {
        return virtualFile.isFile();
    }

    /**
     * Tests whether this item is a folder.
     *
     * @see org.eclipse.che.api.vfs.server.VirtualFile#isFolder()
     */
    public boolean isFolder() {
        return virtualFile.isFolder();
    }

    /**
     * Gets name.
     *
     * @see org.eclipse.che.api.vfs.server.VirtualFile#getName()
     */
    public String getName() {
        return virtualFile.getName();
    }

    /**
     * Gets path.
     *
     * @see org.eclipse.che.api.vfs.server.VirtualFile#getPath()
     */
    public Path getPath() {
        return virtualFile.getPath();
    }

    public String getProject() {
        return project;
    }

    public boolean isProject() {
        return project.equals(getPath());
    }


    /**
     * Deletes this item.
     *
     * @throws ForbiddenException
     *         if delete operation is forbidden
     * @throws ServerException
     *         if other error occurs
     */
    public void remove() throws ServerException, ForbiddenException {
        virtualFile.delete(null);
    }

    public VirtualFile getVirtualFile() {
        return virtualFile;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }


}
