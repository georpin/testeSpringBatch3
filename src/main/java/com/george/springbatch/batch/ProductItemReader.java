package com.george.springbatch.batch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.george.springbatch.model.BookProduct;
import com.george.springbatch.model.MobilePhoneProduct;
import com.george.springbatch.model.Product;

public class ProductItemReader implements ItemReader<Product> {
	
	@Bean
	public ItemReader<Product> productItemReader() throws Exception {
		FlatFileItemReader<Product> reader = new FlatFileItemReader<Product>();
		reader.setResource(new ClassPathResource("multi-products-delimited.txt"));
		reader.setLinesToSkip(1);
		reader.setLineMapper(productLineMapper());
		
		System.out.println("***************************************");

		return reader;
	}

//	
//	public ItemReader<Product> productItemReader() {
//		return new ProductItemWriter();
//	}


	public LineMapper<Product> productLineMapper() throws Exception {
		// HINT: 한 파일에 여러 종류의 데이터가 혼재해 있을 때 씁니다.

		PatternMatchingCompositeLineMapper<Product> mapper = new PatternMatchingCompositeLineMapper<>();

		Map<String, LineTokenizer> tokenizers = new HashMap<String, LineTokenizer>();
		tokenizers.put("PRM*", mobilePhoneProductLineTokenizer());
		tokenizers.put("PRB*", bookProductLineTokenizer());
		mapper.setTokenizers(tokenizers);

		Map<String, FieldSetMapper<Product>> mappers = new HashMap<String, FieldSetMapper<Product>>();
		mappers.put("PRM*", mobilePhoneProductFieldSetMapper());
		mappers.put("PRB*", bookProductFieldSetMapper());
		mapper.setFieldSetMappers(mappers);

		return mapper;
	}
	
	
	public LineTokenizer mobilePhoneProductLineTokenizer() {
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
		tokenizer.setNames(new String[] { "id", "name", "description", "manufacturer", "price" });
		return tokenizer;
	}
	
	
	public LineTokenizer bookProductLineTokenizer() {
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
		tokenizer.setNames(new String[] { "id", "name", "description", "publisher", "price" });
		return tokenizer;
	}


	public FieldSetMapper<Product> mobilePhoneProductFieldSetMapper() throws Exception {
		BeanWrapperFieldSetMapper<Product> mapper =
				new BeanWrapperFieldSetMapper<Product>();

		mapper.setPrototypeBeanName("mobilePhoneProduct");
		mapper.afterPropertiesSet();
		return mapper;
	}
	

	@Scope("prototype")
	public MobilePhoneProduct mobilePhoneProduct() {
		return new MobilePhoneProduct();
	}
	

	public FieldSetMapper<Product> bookProductFieldSetMapper() throws Exception {
		BeanWrapperFieldSetMapper<Product> mapper =
				new BeanWrapperFieldSetMapper<Product>();

		mapper.setPrototypeBeanName("bookProduct");
		mapper.afterPropertiesSet();
		return mapper;
	}
	

	@Scope("prototype")
	public BookProduct bookProduct() {
		return new BookProduct();
	}

	@Override
	public Product read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		return null;
	}
	

}

