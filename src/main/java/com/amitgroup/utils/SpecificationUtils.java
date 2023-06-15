package com.amitgroup.utils;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtils {
    public static Specification addSpecification(Specification initSpecification, Specification specification){
        if (initSpecification != null){
            return initSpecification.and(specification);
        } else {
            return specification;
        }
    }

}
