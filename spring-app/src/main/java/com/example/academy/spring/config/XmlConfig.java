package com.example.academy.spring.config;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class XmlConfig implements WebMvcConfigurer {

    private static final byte[] XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes(StandardCharsets.UTF_8);
    private static final byte[] XSL_PI = "<?xml-stylesheet type=\"text/xsl\" href=\"/xsl/api.xsl\"?>\n".getBytes(StandardCharsets.UTF_8);

    @Bean
    public MappingJackson2XmlHttpMessageConverter xmlMessageConverter() {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, false);
        MappingJackson2XmlHttpMessageConverter base = new MappingJackson2XmlHttpMessageConverter(xmlMapper) {
            @Override
            protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException {
                outputMessage.getBody().write(XML_HEADER);
                outputMessage.getBody().write(XSL_PI);
                super.writeInternal(object, type, outputMessage);
            }
        };
        return base;
    }
}
