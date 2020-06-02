package com.george.springbatch.batch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.george.springbatch.model.Product;


import lombok.Getter;

public class ProductItemWriter implements ItemWriter<Product> {

	@Getter
	private List<Product> products = new ArrayList<Product>();
	
	
	public void write(List<? extends Product> items) throws Exception {
		products.addAll(items);
		for (Product prod : products) System.out.println(prod);
	}
}