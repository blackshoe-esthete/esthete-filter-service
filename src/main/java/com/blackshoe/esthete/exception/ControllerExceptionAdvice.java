package com.blackshoe.esthete.exception;


import com.blackshoe.esthete.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionAdvice {


//    @ExceptionHandler(EnterpriseBlockException.class)
//    public ResponseEntity<ResponseDto> handleEnterpriseBlockException(EnterpriseBlockException e) {
//
//        log.error("EnterpriseBlockException : " + e.getMessage());
//
//        final ResponseDto responseDto = ResponseDto.error(e.getMessage());
//
//        return ResponseEntity.status(e.getHttpStatus()).body(responseDto);
//    }


//    @ExceptionHandler(AuthException.class)
//    public ResponseEntity<ResponseDto> authException(AuthException e){
//
//        log.error("AuthException : " + e);
//
//        final ResponseDto responseDto = ResponseDto.error(e.getMessage().toString());
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleException(Exception e) {

        log.error("Exception " + e);

        if(e.getClass().getName().equals("org.springframework.security.access.AccessDeniedException")){

            final ResponseDto responseDto = ResponseDto.error("권한이 없습니다.");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
        }

        final ResponseDto responseDto = ResponseDto.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }

}