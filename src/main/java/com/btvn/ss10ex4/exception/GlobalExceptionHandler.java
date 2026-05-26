package com.btvn.ss10ex4.exception;

import com.btvn.ss10ex4.model.dto.respponse.ApiDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiDataResponse<Void>> handleBusinessException(BusinessException ex) {
        log.warn("CẢNH BÁO NGHIỆP VỤ: Lỗi [{}] - {}", ex.getErrorCode(), ex.getMessage());

        Map<String, String> errDetails = new HashMap<>();
        errDetails.put("errorCode", ex.getErrorCode());
        errDetails.put("reason", ex.getMessage());

        ApiDataResponse<Void> response = ApiDataResponse.<Void>builder()
                .success(false)
                .message("Yêu cầu không hợp lệ do vi phạm quy tắc nghiệp vụ.")
                .error(errDetails)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiDataResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            log.warn("CẢNH BÁO VALIDATION: Trường [{}] vi phạm ràng buộc: {}", fieldName, errorMessage);
        });

        ApiDataResponse<Map<String, String>> response = ApiDataResponse.<Map<String, String>>builder()
                .success(false)
                .message("Dữ liệu đầu vào không hợp lệ!")
                .error(errors)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiDataResponse<Void>> handleGlobalException(Exception ex) {
        log.error("LỖI HỆ THỐNG NGHIÊM TRỌNG: ", ex);

        ApiDataResponse<Void> response = ApiDataResponse.<Void>builder()
                .success(false)
                .message("Lỗi máy chủ nội bộ. Vui lòng liên hệ quản trị viên")
                .error(ex.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}