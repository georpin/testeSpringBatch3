package com.george.springbatch.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.george.springbatch.batch.ProductItemWriter;
import com.george.springbatch.model.Product;

@EnableBatchProcessing
public class BatchConfiguration {
	@Autowired
    private JobBuilderFactory jobBuilderFactory;
	
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    private ItemReader<Product> productItemReader;
    
    @Autowired
    private ItemWriter<Product> productItemWriter;
    
//      
//    public BatchConfiguration(JobBuilderFactory jobBuilderFactory,
//                              StepBuilderFactory stepBuilderFactory,
//                              DataSource dataSource) {
//        this.jobBuilderFactory = jobBuilderFactory;
//        this.stepBuilderFactory = stepBuilderFactory;
//    }
    
    @Bean
    public Job importProductsJob(JobCompletionNotificationListener listener) throws Exception {
        return jobBuilderFactory.get("importProductsJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }


    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
                .<Product, Product>chunk(10)
                .reader(productItemReader)
//                 .processor(null)
                   .writer(productItemWriter)
                .build();
    }
    
//    @Bean
//    public ProductItemReader productItemReader() {
//        return productItemReader();
//    }
    
 
    public ProductItemWriter productItemWriter() {
        return new ProductItemWriter();
    }
    
    
    
//	@Bean
//	public Job importProductsJob() throws Exception {
//
//		Step step = stepBuilderFactory.get("importProductsJob")
//		                        		.<Product, Product>chunk(100)
//		                        		.reader(itemReader.productItemReader())
//		                        		.writer(itemWriter)
//		                        		.build();
//
//		return jobBuilderFactory.get("importProductsJob")
//		                  .start(step)
//		                  .build();
//	}
//    
    

}
