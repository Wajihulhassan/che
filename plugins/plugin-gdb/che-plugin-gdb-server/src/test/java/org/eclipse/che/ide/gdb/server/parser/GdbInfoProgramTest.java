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
package org.eclipse.che.ide.gdb.server.parser;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * @author Anatoliy Bazko
 */
public class GdbInfoProgramTest {

    @Test
    public void testProgramIsFinished() throws Exception {
        GdbOutput gdbOutput = GdbOutput.of("The program being debugged is not being run.\n");

        GdbInfoProgram gdbInfoProgram = GdbInfoProgram.parse(gdbOutput);
        assertNull(gdbInfoProgram.getStoppedAddress());
    }

    @Test
    public void testProgramIsStopped() throws Exception {
        GdbOutput gdbOutput = GdbOutput.of("Debugging a target over a serial line.\n" +
                                           "Program stopped at 0x7ffff7ddb2d0.\n" +
                                           "It stopped with signal SIGTRAP, Trace/breakpoint trap.\n");

        GdbInfoProgram gdbInfoProgram = GdbInfoProgram.parse(gdbOutput);
        assertEquals(gdbInfoProgram.getStoppedAddress(), "0x7ffff7ddb2d0");
    }
}