package com.example.sdk.models;

import lombok.Value;

@Value
public class Payment{
	String reference;
	int amount;
	String currency;
}
