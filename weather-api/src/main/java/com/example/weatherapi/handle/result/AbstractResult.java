package com.example.weatherapi.handle.result;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class AbstractResult {
    private Boolean success;

    private HttpStatus httpStatus;

    private int httpStatusCode;
    private String message;

    public AbstractResult(final AbstractBuilder<?> builder) {
        this.success = builder.success;
        this.httpStatus = builder.httpStatus;
        this.message = builder.message;
    }


    public abstract static class AbstractBuilder<B extends AbstractBuilder<B>> {
        protected Boolean success;
        protected HttpStatus httpStatus;

        protected int httpStatusCode;
        protected String message;

        public AbstractBuilder() {
        }
        public AbstractBuilder(final HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            this.httpStatusCode = httpStatus.value();
        }

        public B success(Boolean success) {
            this.success = success;
            return self();
        }

        public B httpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            this.httpStatusCode = httpStatus.value();
            return self();
        }

        public B message(String message) {
            this.message = message;
            return self();
        }
        public B success() {
            this.httpStatus = HttpStatus.OK;
            this.httpStatusCode = HttpStatus.OK.value();
            return self();
        }
        public abstract AbstractResult build();
        protected abstract B self();
    }


}
