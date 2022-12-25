package com.example.weatherapi.controller;

import com.example.weatherapi.dto.province.ProvinceSearch;
import com.example.weatherapi.handle.response.HttpResponse;
import com.example.weatherapi.handle.response.HttpResponseError;
import com.example.weatherapi.handle.response.HttpResponseSuccess;
import com.example.weatherapi.handle.result.DataResult;
import com.example.weatherapi.service.province.ProvinceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Controller
@Slf4j
@RequestMapping("/api/v1/province")
@CrossOrigin(origins = "*")
public class ProvinceController {
    private final ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @RequestMapping("/search")
    public ResponseEntity<HttpResponse> getProvinceSearch(@RequestBody ProvinceSearch search) {
        DataResult result = provinceService.getProvincesSearch(search);
        return result.getSuccess() ? ResponseEntity.ok(HttpResponseSuccess.success(result.getData()).build())
                : ResponseEntity.badRequest().body(HttpResponseError.error(result.getHttpStatus(), result.getMessage()).build());
    }

    @GetMapping("/{code}")
    public ResponseEntity<HttpResponse> getProvinceByCode(@NotNull @PathVariable String code) {
        DataResult result = provinceService.getProvinceByCode(code);
        return result.getSuccess() ? ResponseEntity.ok(HttpResponseSuccess.success(result.getData()).build())
                : ResponseEntity.badRequest().body(HttpResponseError.error(result.getHttpStatus(), result.getMessage()).build());
    }
}
