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

import com.jcraft.jsch.JSch;

import org.eclipse.che.plugin.machine.persistent.ssh.SshMachineRecipe;
import org.eclipse.che.plugin.machine.persistent.ssh.jsch.SshClient;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

/**
 * author Alexander Garagatyi
 */
public class PersistentMachineTerminalLauncherTest {
private SshClient sshClient;

    @BeforeMethod
    public void setUp() throws Exception {
        sshClient = new SshClient(new SshMachineRecipe("localhost", 222, "root", "root"), Collections.emptyMap(), new JSch(), 2000);

        sshClient.openConnection();
    }

    @Test
    public void should() throws Exception {
        sshClient.copy("/home/gaal/workspace/che/assembly/assembly-main/target/eclipse-che-4.1.0-RC1-SNAPSHOT/eclipse-che-4.1.0-RC1-SNAPSHOT/lib/terminal/linux_amd64",
                       "~/che/terminal");
    }
}
