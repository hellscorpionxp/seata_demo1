package com.tony.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {

  private static final String SUCCESS = "SUCCESS";
  private static final String FAIL = "FAIL";
  @Autowired
  private JdbcTemplate orderJdbcTemplate;
  @Autowired
  private RestTemplate restTemplate;

  @PostMapping(value = "/order", produces = "application/json")
  public String account(String userId, String commodityCode, int orderCount) {
    String url = "http://127.0.0.1:18081/account";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("userId", userId);
    int orderMoney = orderCount * 100;
    map.add("money", orderMoney + "");
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
    ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
    int result = 0;
    if (response.getStatusCodeValue() == 200) {
      result = orderJdbcTemplate.update(
          "insert into order_tbl (user_id, commodity_code, count, money) values (?, ?, ?, ?)", userId, commodityCode,
          orderCount, orderMoney);
    }
    if (result == 1) {
      return SUCCESS;
    }
    return FAIL;
  }

}
