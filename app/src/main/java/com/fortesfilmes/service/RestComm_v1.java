//package com.fortesfilmes.service;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.client.RestTemplate;
//
//public class RestComm_v1 {
//
//    private static final String TAG = RestComm_v1.class.getSimpleName();
//
//    private static RestComm_v1 INSTANCE;
//
//    //
//    private RestTemplate restTemplate;
//
//    private ObjectMapper mapper;
//    //
////    private String domain = "http://128.30.1.150:8081/loraService";
////    private String domain = "http://jsonplaceholder.typicode.com";
//    private String domain = "";
//
//    private String uri = "/employed/list";
//
//
//    /**
//     *
//     */
//    public RestComm_v1() {
//        restTemplate = new RestTemplate(true);
//        mapper = new ObjectMapper();
//        //
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
////        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
////        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
////        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
////        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
//
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setObjectMapper(mapper);
//        restTemplate.getMessageConverters().add(converter);
//
//        // --------------------------------------------------------
//        // Redirect
//        final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//        final HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
//        factory.setHttpClient(httpClient);
//        restTemplate.setRequestFactory(factory);
//        // --------------------------------------------------------
//    }
//
//    /**
//     *
//     */
//    public static RestComm_v1 getInstance() {
//        if (INSTANCE == null) {
//            INSTANCE = new RestComm_v1();
//        }
//        return INSTANCE;
//    }
//}
