package com.ecommerce.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    List<CategoryDTO> content;
    Integer pageNumber;
    Integer pageSize;
    Integer totalPages;
    Long totalElements;
    Boolean lastPage;
}
