package com.elegoo.cloud.module.mqtt.access.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JacksonUtil {
	private static Logger logger = LoggerFactory.getLogger(JacksonUtil.class);

    private final static ObjectMapper objectMapper = getObjectMapper() ;
    private final static ObjectMapper notNullMapper = getNotNullObjectMapper();

    private static ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
    	//忽略不存在的字段
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//所有日期格式返回毫秒数
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,true);
		//禁用报错空对象
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return objectMapper;
    }

	private static ObjectMapper getNotNullObjectMapper() {
		ObjectMapper objectMapper = getObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // 配置不序列化null值
		return objectMapper;
	}

	public static ObjectMapper getInstance(){
		return objectMapper;
	}

    /**
     * bean、array、List、Map --> json
     *
     * @param obj
     * @return json string
     * @throws Exception
     */
    public static String writeValueAsString(Object obj) {
    	try {
			return obj == null ? null : objectMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
        return null;
    }

	public static String writeNotNullValueAsString(Object obj) {
		try {
			return obj == null ? null : notNullMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

    /**
     * string --> bean、Map、List(array)
     *
     * @param content
     * @param clazz
     * @return obj
     * @throws Exception
     */
    public static <T> T readValue(String content, Class<T> clazz) {
		if(StrUtil.isEmpty(content)) {
			return null ;
		}
		try {
			return objectMapper.readValue(content, clazz);
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
    	return null;
    }

	public static <T> List<T> readListValue(String content, Class<T> elementClass) {
		if (StrUtil.isEmpty(content)) return null;
		try {
			return objectMapper.readValue(content, objectMapper.getTypeFactory().constructCollectionType(List.class, elementClass));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public static Map<String,Object> readToMap(String content) {
		if(StrUtil.isEmpty(content)) {
			return null ;
		}
		try {
			return objectMapper.readValue(content, Map.class);
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * string --> bean、Map、List(array)
	 *
	 * @param content
	 * @param valueTypeRef
	 * @return obj
	 * @throws Exception
	 */
	public static <T> T readValue(String content, TypeReference<T> valueTypeRef) {
		if(StrUtil.isEmpty(content)) {
			return null ;
		}
		try {
			return objectMapper.readValue(content, valueTypeRef);
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * string --> List<Bean>...
	 *
	 * @param jsonStr
	 * @param parametrized
	 * @param parameterClasses
	 * @param <T>
	 * @return
	 */
	public static <T> T readValue(String jsonStr, Class<?> parametrized, Class<?>... parameterClasses) {
		try {
			JavaType javaType = objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
			return objectMapper.readValue(jsonStr, javaType);
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static <T> T objToBean(Object obj, Class<T> clazz) {
		return readValue(writeValueAsString(obj), clazz);
	}

	public static Map<String,Object> objToMap(Object obj){
		if(null == obj){
			return MapUtil.newHashMap();
		}
		return readValue(writeValueAsString(obj), Map.class);
	}

	public static <T> List<T> objToBeanList(Object obj, Class<T> clazz) {
		return readListValue(writeValueAsString(obj), clazz);
	}
}
