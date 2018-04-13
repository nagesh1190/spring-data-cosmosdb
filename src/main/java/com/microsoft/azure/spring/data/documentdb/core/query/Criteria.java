/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.spring.data.documentdb.core.query;

import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Criteria {

    private CriteriaType type;
    private String subject;
    private List<Criteria> criteriaList;
    private List<Object> criteriaValues;

    public Criteria(CriteriaType criteriaType) {
        this.type = criteriaType;
        this.criteriaList = new ArrayList<>();
    }

    public static Criteria getInstance(@NonNull String subject, CriteriaType type, @NonNull List<Object> values) {
        final Criteria criteria = new Criteria(type);

        criteria.subject = subject;
        criteria.criteriaValues = values;

        return criteria;
    }

    public static Criteria getAndInstance(@NonNull Criteria left, @NonNull Criteria right) {
       final Criteria criteria = new Criteria(CriteriaType.AND);

       criteria.criteriaList.add(left);
       criteria.criteriaList.add(right);

       return criteria;
    }

    public static Criteria getOrInstance(@NonNull Criteria left, @NonNull Criteria right) {
        final Criteria criteria = new Criteria(CriteriaType.OR);

        criteria.criteriaList.add(left);
        criteria.criteriaList.add(right);

        return criteria;
    }
}
