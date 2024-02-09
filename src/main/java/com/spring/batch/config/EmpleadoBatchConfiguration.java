package com.spring.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.spring.batch.model.Logging;

import javax.sql.DataSource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;


@Configuration
@EnableBatchProcessing
public class EmpleadoBatchConfiguration {

	@Autowired
	private StepBuilderFactory stepBuilderF;
	
	@Autowired
	private JobBuilderFactory jobBuilderF;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
    public CustomJsonItemReader<Logging> readFromMicroservice() {
        return new CustomJsonItemReader<>("http://localhost:8081/logging/lista", Logging.class);
    }
	@Bean
    public JdbcBatchItemWriter<Logging> writerIntoDB() {
        JdbcBatchItemWriter<Logging> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("INSERT INTO csvtodbdatamicroservice (id,nomUsuario,apPaterno,apMaterno) VALUES (:id,:nomUsuario,:apPaterno,:apMaterno)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }
	@Bean
	public Step step() {
		ItemReader<Logging> jsonItemReader = new CustomJsonItemReader<>("http://localhost:8081/logging/lista", Logging.class); // Cambio aquí
	    return stepBuilderF.get("step13")
	            .<Logging, Logging>chunk(1)
	            .reader(jsonItemReader) // Utilizar el lector modificado
	            .writer(writerIntoDB())
	            .build();
	}
	@Bean
	public Job job() {
		return jobBuilderF.get("job5").flow(step()).end().build();
	}
}