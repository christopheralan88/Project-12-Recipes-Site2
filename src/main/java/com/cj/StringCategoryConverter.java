package com.cj;


import com.cj.dao.CategoryDao;
import com.cj.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class StringCategoryConverter implements Converter<String, Category> {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public Category convert(String source) {
        return categoryDao.findOne(Long.parseLong(source));
    }

    @Bean
    public ConversionService getConversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();

        Set<Converter> converters = new HashSet<>();
        converters.add(new StringCategoryConverter());

        bean.setConverters(converters);

        return bean.getObject();
    }
}