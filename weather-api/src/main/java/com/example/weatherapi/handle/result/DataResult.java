package com.example.weatherapi.handle.result;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DataResult extends AbstractResult {
    private Object data;

    public DataResult(Builder builder) {
        super(builder);
        this.data = builder.data;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder success() {
        return new Builder(HttpStatus.OK).message("Success!").success(Boolean.TRUE);
    }

    public static Builder success(final Object data) {
        return success().data(data);
    }

    public static Builder error(HttpStatus httpStatus, String message) {
        return builder().data(null).httpStatus(httpStatus).success(Boolean.FALSE).message(message);
    }

    public static class Builder extends AbstractBuilder<Builder> {
        protected Object data;

        protected Builder() {
            super();
        }

        protected Builder(final HttpStatus httpStatus) {
            super(httpStatus);
        }

        public Builder data(final Object data) {
            this.data = data;
            return self();
        }

        @Override
        public DataResult build() {
            return new DataResult(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
