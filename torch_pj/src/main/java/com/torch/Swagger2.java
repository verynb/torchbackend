package com.torch;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by yuanj on 2016/11/7.
 */

@Configuration
@EnableSwagger2
@Profile("swagger")
public class Swagger2 {

  private final static Parameter tokenParameter = new ParameterBuilder()
      .name("x-auth-token").description("认证令牌")
      .parameterType("header").defaultValue("xxxxx-admin").modelRef(new ModelRef("string"))
      .build();
  private final static String TEST_HOST = "116.62.208.39:8080";

  @Bean
  public Docket searchRestApi4() {

    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("火炬助学后端api")
        .apiInfo(apiInfo())
        .host(TEST_HOST)
        .globalOperationParameters(Lists.newArrayList(tokenParameter))
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.torch.interfaces"))
        .paths(PathSelectors.any())
        .build();

  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("火炬助学RESTful APIs")
        .description(
            "根据restful风格, 默认按照如下约定:\r" +
                "GET（SELECT）：从服务器取出资源（一项或多项）。\r" +
                "POST（CREATE）：在服务器新建一个资源。\r" +
                "PUT（UPDATE）：在服务器更新资源（客户端提供改变后的完整资源）。\r" +
                "PATCH（UPDATE）：在服务器更新资源（客户端提供改变的属性）。\r" +
                "DELETE（DELETE）：从服务器删除资源。")
        .version("1.0")
        .build();
  }

}
