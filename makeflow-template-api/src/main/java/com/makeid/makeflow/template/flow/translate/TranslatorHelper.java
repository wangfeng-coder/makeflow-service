package com.makeid.makeflow.template.flow.translate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-14
 */
public class TranslatorHelper {

    public static final<S,R> List<R> toList(List<S> source, Function<S,R> mapping){
        return Optional.ofNullable(source)
                .orElse(new ArrayList<>())
                .stream()
                .map(mapping)
                .collect(Collectors.toList());
    }
}
