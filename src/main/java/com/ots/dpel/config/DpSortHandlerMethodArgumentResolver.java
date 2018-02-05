package com.ots.dpel.config;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.List;

public class DpSortHandlerMethodArgumentResolver extends SortHandlerMethodArgumentResolver {
    
    private static final String DEFAULT_ORDER = "sord";
    private String sortOrder = DEFAULT_ORDER;
    private String defaultSortOrderValue = "asc";
    private String defaultSortParameterValue = "id";
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public void setDefaultSortOrderValue(String defaultSortOrderValue) {
        this.defaultSortOrderValue = defaultSortOrderValue;
    }
    
    public void setDefaultSortParameterValue(String defaultSortParameterValue) {
        this.defaultSortParameterValue = defaultSortParameterValue;
    }
    
    @Override
    public Sort resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory) {
        
        String directionParameter = webRequest.getParameter(sortOrder);
        
        if (directionParameter == null || directionParameter.isEmpty()) directionParameter = defaultSortOrderValue;
        
        String sortParameter = webRequest.getParameter(getSortParameter(null));
        
        if (sortParameter == null || sortParameter.isEmpty()) sortParameter = defaultSortParameterValue;
        
        if (directionParameter != null && sortParameter != null) {
            // Multisort
            if (sortParameter.contains(",")) {
                String[] sortParams = sortParameter.split(",");
                List<Sort.Order> orderList = new ArrayList<>();
                
                for (int i = 0; i < sortParams.length; i++) {
                    String[] sorts = sortParams[i].trim().split(" ");
                    orderList.add(i == sortParams.length - 1
                        ? new Sort.Order(Direction.fromString(directionParameter), sorts[0])
                        : new Sort.Order(Direction.fromString(sorts[1]), sorts[0]));
                }
                
                return new Sort(orderList);
            }
            
            return new Sort(Direction.fromString(directionParameter), sortParameter);
        } else  {
            return null;
        }
    }
}