package com.ey.common;


public record ApiError(String timestamp, int status, String code, String message) {}


