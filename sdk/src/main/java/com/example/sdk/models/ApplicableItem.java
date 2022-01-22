package com.example.sdk.models;

import java.util.List;
import lombok.Value;

@Value
public class ApplicableItem{
	String recurrence;
	boolean redirect;
	String code;
	String method;
	String registration;
	Links links;
	String operationType;
	String label;
	String grouping;
	boolean selected;
	List<InputElementsItem> inputElements;
	ContractData contractData;
}
