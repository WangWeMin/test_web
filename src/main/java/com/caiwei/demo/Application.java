package com.caiwei.demo;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableCaching
@EnableFeignClients
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * 替换json框架为fastjson
	 */
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		//1.需要定义一个convert转换消息的对象;
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		//2:添加fastJson的配置信息;
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		//指定当属性值为null是是否输出：pro:null
		fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
		//3处理中文乱码问题
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		//4.在convert中添加配置信息.
		fastConverter.setSupportedMediaTypes(fastMediaTypes);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		//返回构成用的组件Bean
		return new HttpMessageConverters((HttpMessageConverter<?>) fastConverter);
	}

}
