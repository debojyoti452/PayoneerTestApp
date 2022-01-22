package com.example.sdk.models;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode
@Value
public class Response {
    String resultCode;
    Networks networks;
    String resultInfo;
    ReturnCode returnCode;
    Identification identification;
    String integrationType;
    Interaction interaction;
    Links links;
    String operationType;
    Style style;
    Payment payment;
    String operation;
    String timestamp;
    Status status;
}
