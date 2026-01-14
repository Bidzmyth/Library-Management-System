package com.ey.common;

public record ApiResponse<T>(String message, T data) {}
