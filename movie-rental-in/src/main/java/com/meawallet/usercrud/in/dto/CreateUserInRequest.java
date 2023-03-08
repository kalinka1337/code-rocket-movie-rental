package com.meawallet.usercrud.in.dto;


import java.math.BigDecimal;

public record CreateUserInRequest(String name, Integer age, BigDecimal credits) {

}
