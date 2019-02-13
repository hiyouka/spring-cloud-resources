![img](http://ww1.sinaimg.cn/large/007BVBG7gy1g03gshx34dj30k808wwei.jpg)

## Directory Structure
```shell
├── sourcce-duboo                                    // common package.  
│   ├── src/main
│   ├── ├──java/com/crossoverJie/sbcorder/common  // Specific code.  
│   ├── ├──resources
├── sbc-gateway-zuul                              // gateWay.  
│   ├── src/main
│   ├── ├──java/com/crossoverJie/gateway/zuul     // Specific code.                    :8383 port.
│   ├── ├──resources
├── sbc-order                                     // order app                         :8181 port.
│   ├── src/main
│   ├── ├──java/com/crossoverJie/sbcorder         // Specific code.
│   ├── ├──resources
├── sbc-service                                   // eureka-server Registration center :8888 port.
│   ├── src/main
│   ├── ├──java/com/crossoverJie/service/         // Specific code.
│   ├── ├──resources
├── sbc-user                                      // user app.                         :8080 port.
│   ├── src/main
│   ├── ├──java/com/crossoverJie/sbcuser/
│   ├── ├──resources
├── sbc-request-check                             // remove duplicates starter.
│   ├── src/main
│   ├── ├──com/crossoverJie/request/check         // Specific code. 
│   ├── ├──├──anotation                           // anotation package.
│   ├── ├──├──conf                                // Automate configuration-related code.
│   ├── ├──├──interceptor                         // Aspect.
│   ├── ├──├──properties                          // configuration.
│   ├── ├──resources
│   ├── ├──├──META-INF
│   ├── ├──├──├──spring.factories                 // Automated configuration files.
├── sbc-hystix-turbine                            // Hystrix Dashboard                 :8282 port.
│   ├── src/main
│   ├── ├──java/com/crossoverJie/hystrix/turbine
│   ├── ├──resources
├── .gitignore                                    
├── LICENSE                
├── README.md               


```
