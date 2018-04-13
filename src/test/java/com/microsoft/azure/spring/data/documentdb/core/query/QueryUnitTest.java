/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.spring.data.documentdb.core.query;

import com.microsoft.azure.spring.data.documentdb.TestConstants;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class QueryUnitTest {

    @Test
    public void testConstructor() {
        final Criteria criteria = Criteria.getInstance(TestConstants.CRITERIA_KEY, CriteriaType.IS_EQUAL,
                Arrays.asList(new Object[]{TestConstants.CRITERIA_OBJECT}));
        final Query query = new Query(criteria);

        Assert.assertEquals(query.getCriteria(), criteria);
    }
}
