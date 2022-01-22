package com.example.sdk.models;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode
@Value
public class Response {
    String resultCode;
    Networks networks;
    String resultInfo;
    String integrationType;
    String operationType;
    String operation;
    String timestamp;
}
