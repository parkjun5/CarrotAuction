package com.carrot.core.common.exception.code;

import com.carrot.core.common.exception.BusinessException;
import com.carrot.core.common.exception.custom.BadRequestException;
import com.carrot.core.common.exception.custom.NotFoundErrorCodeException;
import com.carrot.core.common.exception.custom.UnauthorizedException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    NOT_FOUND_ERROR_CODE(0, "해당 에러의 에러코드를 찾을 수 없습니다.", NotFoundErrorCodeException.class),
    BAD_REQUEST(400, "잘못된 요청입니다.", BadRequestException.class),
    UNAUTHORIZED(401, "로그인이 필요한 서비스 입니다.", UnauthorizedException.class),
    INTERNAL_SERVER_ERROR(500, "서버가 요청을 응답할 수 없는 상태입니다.", BusinessException.class),
    METHOD_ARGUMENT_NOT_VALID(400, "잘못된 요청입니다.", MethodArgumentNotValidException.class),
    ;

    private final int code;
    private final String message;
    private final Class<? extends Exception> type;

    public static ExceptionCode findByClass(Class<?> type) {
        return Arrays.stream(ExceptionCode.values())
                .filter(each -> each.type.equals(type))
                .findAny()
                .orElseThrow(NotFoundErrorCodeException::new);
    }
}
