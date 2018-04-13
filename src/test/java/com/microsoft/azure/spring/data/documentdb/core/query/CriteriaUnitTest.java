/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.spring.data.documentdb.core.query;

import com.microsoft.azure.spring.data.documentdb.TestConstants;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class CriteriaUnitTest {

    @Test
    public void testSimpleCriteria() {
        final Criteria c = Criteria.getInstance(TestConstants.CRITERIA_KEY, CriteriaType.IS_EQUAL,
                Arrays.asList(new Object[] {TestConstants.CRITERIA_OBJECT}));

        assertThat(c.getSubject()).isEqualTo(TestConstants.CRITERIA_KEY);
        assertThat(c.getCriteriaValues().get(0)).isEqualTo(TestConstants.CRITERIA_OBJECT);
    }
}
