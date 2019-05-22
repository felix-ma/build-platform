package cn.felix.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Felix
 * @date 2019/5/22
 **/
@RestController
public class TestController {
    @GetMapping("hello")
    public String hello() {
        return "hello world";
    }
}
