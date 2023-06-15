package com.amitgroup.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

@Getter
@Setter
public class BasePaginationRequest<T> {

    @Schema(description = "Page number", example = "1", defaultValue = "1", required = false)
    private Integer pageNumber = 1;

    @Schema(description = "Page size", example = "20", defaultValue = "20", required = false)
    private Integer pageSize = 20;

    private Set<BaseSortRequest> sorts = new HashSet<>();

    @JsonIgnore
    private Set<String> sortFields = new HashSet<>(List.of(new String[]{"id"}));

    @JsonIgnore
    private final Set<String> sortDirections = new HashSet<>(List.of(new String[]{"ASC", "DESC"}));


    @Getter
    @Setter
    public static class BaseSortRequest {

        @Schema(description = "Sort field", example = "id", required = false)
        private String fieldName;

        @Schema(description = "Sort direction", allowableValues = {"ASC", "DESC"}, example = "ASC", required = false)
        private String direction;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BaseSortRequest that = (BaseSortRequest) o;
            return fieldName.equals(that.fieldName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(fieldName);
        }
    }

    @JsonIgnore
    public Specification<T> getSpecification() {
        return null;
    }

    public Pageable initPageable() throws ApiException {
        try {
            // validation
            validatePageNumber(this.pageNumber);
            validatePageSize(this.pageSize);
            validateSorts(sorts);

            List<Sort.Order> orders = new ArrayList<>();

            for (BaseSortRequest sort : sorts) {
                Sort.Order order = new Sort.Order(Sort.Direction.valueOf(sort.getDirection()), sort.getFieldName());
                orders.add(order);
            }

            // use default sort if empty
            if (orders.isEmpty()){
                Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
                orders.add(order);
            }

            return PageRequest.of(this.pageNumber - 1, this.pageSize, Sort.by(orders));

        } catch (Exception e) {
            throw new ApiException(ERROR.INVALID_REQUEST, e.getMessage());
        }
    }

    private void validatePageNumber(Integer pageNumber) throws ApiException {
        if (
                pageNumber != null
                        && pageNumber < 1
        ) {
            throw new ApiException(ERROR.INVALID_REQUEST, "Invalid pageNumber");
        }
    }

    private void validatePageSize(Integer pageSize) throws ApiException {
        if (
                pageSize != null
                        && pageSize < 1
        ) {
            throw new ApiException(ERROR.INVALID_REQUEST, "Invalid pageSize");
        }
    }

    private void validateSorts(Set<BaseSortRequest> sorts) throws ApiException {
        for (BaseSortRequest sort : sorts) {
            if (!getSortFields().contains(sort.getFieldName())) {
                throw new ApiException(ERROR.INVALID_REQUEST, "Invalid pageSortField");
            }
            if (!sortDirections.contains(sort.getDirection())) {
                throw new ApiException(ERROR.INVALID_REQUEST, "Invalid pageSortDirection");
            }
        }
    }
}
