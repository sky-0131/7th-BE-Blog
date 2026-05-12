package com.example.blog7th.global.config.swagger;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class ApiErrorCodeExampleCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        ApiErrorCodeExample annotation = handlerMethod.getMethodAnnotation(ApiErrorCodeExample.class);

        if (annotation != null) {
            String errorCode = annotation.value();
            ApiResponses responses = operation.getResponses();


            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setDescription("에러 코드: " + errorCode);
            responses.addApiResponse("400", apiResponse);
        }
        return operation;
    }
}
