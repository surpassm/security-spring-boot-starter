# 敏捷开发权限验证模块
. 在yml文件中配置
~~~
server:
  port: 8182

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/test?serverTimezone=Hongkong&useSSL=false&useUnicode=true&characterEncoding=UTF-8&nullCatalogMeansCurrent=true
    hikari:
      username: root
      password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver

  redis:
    database: 2
    host: localhost
    port: 6379
#    password: liaoin_pm
    password:
    jedis:
      pool:
        # 连接池最大连接数（使用负值表示没有限制）
        max-active: -1
        # 连接池最大阻塞等待时间（使用负值表示没有限制
        max-wait: -1
        # 连接池中的最大空闲连接
        max-idle: 8
        # 连接池中的最小空闲连接
        min-idle: 0

  jpa:
    show-sql: false
    hibernate:
      ddl-auto: update
    database: mysql
    properties:
      hibernate:
        format_sql: false
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect

  servlet:
    multipart:
      enabled: true
      max-file-size: 50MB
      max-request-size: 50MB

  session:
    store-type: none

  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
# 参考配置https://www.cnblogs.com/liaojie970/p/8857710.html
  activiti:
    # 自动建表
    database-schema: TEST
    database-schema-update: true
    history-level: full
    db-history-used: true


surpassm:
  security:
    o-auth2:
      clients[0]:
        clientId: user_1
        clientIdSecret: 123456
      clients[1]:
        clientId: user_2
        clientIdSecret: 123456
    login-type: JSON
#      登陆页面设置
    no-verify[0]: /swagger-**
    no-verify[1]: /images/**
    no-verify[2]: /webjars/**
    no-verify[3]: /v2/api-docs**
    no-verify[4]: /swagger-resources/configuration/ui**
    no-verify[5]: /swagger-resources/configuration/security**
    no-verify[6]: /websocket/socketServer.ws**
    no-verify[7]: /sockjs/socketServer.ws**
    no-verify[8]: /login/refreshToken**
    no-verify[9]: /mobile/getPhone**
    no-verify[10]: /menu/resourcesUpdate**
    no-verify[11]: /file/getFileNameUrl**
    no-verify[12]: /file/listUploadedFiles**
    no-verify[13]: /file/getPath**
#    用户名密码登录请求处理url
    default-login-processing-url-from: /authentication/form
#    default-property-inclusion: NON_NULL

swagger:
  enabled: true
  title: "权限框架 API"
  docket:
    user:
      title: "用户"
      base-package: com.example.demo.controller.user
    common:
      title: "公共"
      base-package: com.example.demo.controller.common
    mobile:
      title: "移动端"
      base-package: com.example.demo.controller.mobile

mybatis:
  type-aliases-package: com.example.demo.controller
  mapper-locations: classpath:mapper/*.xml

logging:
  level:
    com.example.demo.mapper.user: trace
~~~

可以参考 https://github.com/surpassm/parent-spring-boot-starter  demo-mybatis和demo-JPA示例使用