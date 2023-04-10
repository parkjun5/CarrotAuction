package com.carrot.web_mvc.carrotauction.common.exception;

import com.carrot.web_mvc.common.exception.code.ExceptionCode;
import com.carrot.web_mvc.common.exception.custom.NotFoundErrorCodeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class ExceptionCodeTest {
    
    @Test
    @DisplayName("enum을 통한 예외 생성")
    void createCustomExceptionByCode() throws Exception {
        //given
        ExceptionCode notFoundErrorCode = ExceptionCode.NOT_FOUND_ERROR_CODE;
        //then
        assertThat(notFoundErrorCode.getCode()).isZero();
        assertThat(notFoundErrorCode.getMessage()).isEqualTo("해당 에러의 에러코드를 찾을 수 없습니다.");
        assertThat(notFoundErrorCode.getType()).isEqualTo(NotFoundErrorCodeException.class);
    }

}