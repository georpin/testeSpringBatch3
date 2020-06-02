package com.george.springbatch.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookProduct extends Product {

	private String publisher;

	private static final long serialVersionUID = -3928693529337055437L;

	@Override
	public String toString() {
		return "BookProduct [publisher=" + publisher + "]";
	}
	
	
}
