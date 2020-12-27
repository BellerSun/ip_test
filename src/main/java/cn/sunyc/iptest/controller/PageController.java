package cn.sunyc.iptest.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author ：sun yu chao
 */
@Slf4j
@Controller
public class PageController {


    @GetMapping(value = {"", "/", "index", "index.html"})
    public String indexPage(HttpServletRequest request) {
        final String remoteIP = getRemoteIP(request);
        request.setAttribute("remoteIP", remoteIP);
        return "index.html";
    }

    @ResponseBody
    @PostMapping(value = {"", "/", "index", "index.html"})
    public String indexPost(HttpServletRequest request) {
        return getRemoteIP(request);
    }

    /**
     * 获取访问者的外网IP
     *
     * @param request 访问请求
     * @return 外网ip
     */
    private String getRemoteIP(HttpServletRequest request) {
        final String uuid = UUID.randomUUID().toString();
        String ip = request.getHeader("X-Forwarded-For");
        log.info("[PageController] getRemoteIP. requestId:{},header[X-Forwarded-For]:{}", uuid, JSON.toJSONString(ip));
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        log.info("[PageController] getRemoteIP. requestId:{},header[X-Real-IP]:{}", uuid, JSON.toJSONString(ip));
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getRemoteAddr();
        log.info("[PageController] getRemoteIP. requestId:{},header[getRemoteAddress]:{}", uuid, JSON.toJSONString(ip));
        return ip;
    }
}
