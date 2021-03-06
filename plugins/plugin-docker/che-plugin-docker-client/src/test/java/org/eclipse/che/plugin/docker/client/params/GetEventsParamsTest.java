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

import org.eclipse.che.plugin.docker.client.json.Filters;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Mykola Morhun
 */
public class GetEventsParamsTest {

    private static final long    SINCE_SECOND = 12345L;
    private static final long    UNTIL_SECOND = 67890L;
    private static final Filters FILTERS      = mock(Filters.class);

    private GetEventsParams getEventsParams;

    @BeforeMethod
    private void prepare() {
        getEventsParams = GetEventsParams.from();
    }

    @Test
    public void shouldCreateParamsObjectWithRequiredParameters() {
        getEventsParams = GetEventsParams.from();

        assertNull(getEventsParams.getSinceSecond());
        assertNull(getEventsParams.getUntilSecond());
        assertNull(getEventsParams.getFilters());
    }

    @Test
    public void shouldCreateParamsObjectWithAllPossibleParameters() {
        getEventsParams = GetEventsParams.from()
                                         .withSinceSecond(SINCE_SECOND)
                                         .withUntilSecond(UNTIL_SECOND)
                                         .withFilters(FILTERS);

        assertTrue(getEventsParams.getSinceSecond() == SINCE_SECOND);
        assertTrue(getEventsParams.getUntilSecond() == UNTIL_SECOND);
        assertEquals(getEventsParams.getFilters(), FILTERS);
    }

    @Test
    public void sinceSecondsParameterShouldEqualsNullIfItNotSet() {
        getEventsParams.withUntilSecond(UNTIL_SECOND)
                       .withFilters(FILTERS);

        assertNull(getEventsParams.getSinceSecond());
    }

    @Test
    public void untilSecondsParameterShouldEqualsNullIfItNotSet() {
        getEventsParams.withSinceSecond(SINCE_SECOND)
                       .withFilters(FILTERS);

        assertNull(getEventsParams.getUntilSecond());
    }

    @Test
    public void filtersParameterShouldEqualsNullIfItNotSet() {
        getEventsParams.withSinceSecond(SINCE_SECOND)
                       .withUntilSecond(UNTIL_SECOND);

        assertNull(getEventsParams.getFilters());
    }

}
