package uz.pdp.market.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.pdp.market.exception.exceptions.NotFoundException;

@ControllerAdvice("uz.pdp.market")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<AppError> notFoundExceptionHandler(NotFoundException e, WebRequest request) {
        return new ResponseEntity<>(AppError
                .builder()
                .message(e.getMessage())
                .request(request)
                .status(HttpStatus.NOT_FOUND)
                .build(), HttpStatus.NOT_FOUND);
    }

}
