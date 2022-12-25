package com.example.weatherapi.handle.result;

import org.springframework.http.HttpStatus;

public class BaseResult extends AbstractResult {

    public BaseResult(AbstractBuilder<?> builder) {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder success() {
        return new Builder(HttpStatus.OK).message("Success!").success(Boolean.TRUE);
    }
    public static Builder error(HttpStatus httpStatus, String message) {
        return new Builder(httpStatus).message(message).success(Boolean.FALSE);
    }

    public static class Builder extends AbstractBuilder<Builder> {
        public Builder() {
            super();
        }

        public Builder(final HttpStatus httpStatus) {
            super(httpStatus);
        }

        @Override
        public BaseResult build() {
            return new BaseResult(this);
        }

        @Override
        protected Builder self() {
            return this;
        }


    }
}
