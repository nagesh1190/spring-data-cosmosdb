/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.spring.data.documentdb.repository.query;

import com.microsoft.azure.spring.data.documentdb.core.mapping.DocumentDbPersistentProperty;
import com.microsoft.azure.spring.data.documentdb.core.query.Criteria;
import com.microsoft.azure.spring.data.documentdb.core.query.CriteriaType;
import com.microsoft.azure.spring.data.documentdb.core.query.Query;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.query.parser.AbstractQueryCreator;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.data.repository.query.parser.PartTree;
import org.springframework.lang.NonNull;

import java.util.*;


public class DocumentDbQueryCreator extends AbstractQueryCreator<Query, Criteria> {

    private final MappingContext<?, DocumentDbPersistentProperty> mappingContext;

    private static final Map<Part.Type, CriteriaType> criteriaMap;

    static {
        final Map<Part.Type, CriteriaType> map = new HashMap<>();

        map.put(Part.Type.BETWEEN, CriteriaType.BETWEEN);
        map.put(Part.Type.SIMPLE_PROPERTY, CriteriaType.IS_EQUAL);

        criteriaMap = Collections.unmodifiableMap(map);
    }

    public DocumentDbQueryCreator(PartTree tree, DocumentDbParameterAccessor accessor,
                                  MappingContext<?, DocumentDbPersistentProperty> mappingContext) {
        super(tree, accessor);

        this.mappingContext = mappingContext;
    }

    private Criteria createCriteria(@NonNull Part part, @NonNull Iterator<Object> parameters) {
        final Part.Type type = part.getType();
        final String subject = this.mappingContext.getPersistentPropertyPath(part.getProperty()).toDotPath();
        final List<Object> values = new ArrayList<>();

        for (int i = 0; i < part.getNumberOfArguments(); i++) {
            values.add(parameters.next());
        }

        if (criteriaMap.containsKey(type)) {
            final Criteria criteria = Criteria.getInstance(subject, criteriaMap.get(type), values);

            return criteria;
        }

        throw new IllegalArgumentException("unsupported keyword: " + type.toString());
    }

    @Override
    protected Criteria create(Part part, Iterator<Object> iterator) {
        return this.createCriteria(part, iterator);
    }

    @Override
    protected Criteria and(@NonNull Part part, @NonNull Criteria base, @NonNull Iterator<Object> iterator) {
        final Criteria right = this.createCriteria(part, iterator);

        return Criteria.getAndInstance(base, right);
    }

    @Override
    protected Criteria or(@NonNull Criteria base, @NonNull Criteria criteria) {
        return Criteria.getOrInstance(base, criteria);
    }

    @Override
    protected Query complete(Criteria criteria, Sort sort) {
        return new Query(criteria);
    }
}
