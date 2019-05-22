package cn.felix;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
public class Application {
    private static final String SPRING_CONFIG_NAME_KEY = "--spring.config.name";
    public static final String DEFAULT_SPRING_CONFIG_PARAM = SPRING_CONFIG_NAME_KEY + "=" + "build-platform";


    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        Environment env = app.run(updateArguments(args)).getEnvironment();
        log(env);
    }

    private static String[] updateArguments(String[] args) {
        if (Arrays.stream(args).noneMatch(arg -> arg.startsWith(SPRING_CONFIG_NAME_KEY))) {
            String[] modifiedArgs = new String[args.length + 1];
            System.arraycopy(args, 0, modifiedArgs, 0, args.length);
            modifiedArgs[args.length] = DEFAULT_SPRING_CONFIG_PARAM;
            return modifiedArgs;
        }
        return args;
    }

    /**
     * ajax 跨域设置
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration ajax = new CorsConfiguration();
        ajax.addAllowedOrigin("*");
        ajax.addAllowedHeader("*");
        ajax.addAllowedMethod("*");
        Map<String, CorsConfiguration> map = new LinkedHashMap<>();
        map.put("/**", ajax);
        source.setCorsConfigurations(map);
        return new CorsFilter(source);
    }

    /**
     * 格式化运行成功后输出项目地址
     *
     * @param env
     */
    private static void log(Environment env) {
        String name = env.getProperty("spring.application.name");
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        String ssl = env.getProperty("server.ssl.enabled");
        String http = "true".equals(ssl) ? "https" : "http";
        String address = null;
        try {
            address = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            address = "127.0.0.1";
            e.printStackTrace();
        }
        name = StringUtils.isEmpty(name) ? "" : name;
        port = StringUtils.isEmpty(port) ? "8080" : port;
        path = StringUtils.isEmpty(path) ? "" : path;
        LoggerFactory.getLogger(Application.class).info(
                "\n----------------------------------------------------------\n"
                        + "\tApplication '{}' is running! Access URLs:\n"
                        + "\tLocal: \t\t{}://localhost:{}{}\n"
                        + "\tExternal: \t{}://{}:{}{}\n"
                        + "\tSwagger: \t{}://{}:{}{}/swagger-ui.html\n"
                        + "----------------------------------------------------------",
                name, http, port, path, http, address, port, path, http, address, port, path);
    }

}
