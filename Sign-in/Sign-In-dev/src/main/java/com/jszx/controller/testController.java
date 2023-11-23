package com.jszx.controller;


import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.jszx.test.GetMacAddress;
import com.jszx.utils.IpMacUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;




@ApiSupport(author = "xiaoymin@foxmail.com",order = 284)
@RestController
@CrossOrigin
@RequestMapping("test")
public class testController {
    @GetMapping("/mac")
    public String test(HttpServletRequest request){
        String http_x_forwarded_for = request.getHeader("HTTP_X_FORWARDED_FOR");
        System.out.println("https_x_forwarded_for = " + http_x_forwarded_for);
        String x_forwarded_for = request.getHeader("x-forwarded-for");
        System.out.println("x_forwarded_for = " + x_forwarded_for);
        String clientIP = IpMacUtil.getClientIP(request);
        System.out.println("clientIP = " + clientIP);
        String ipAddr = getIpAddr(request);
        System.out.println("ipAddr = " + ipAddr);
        String remoteAddr = request.getRemoteAddr();
        System.out.println("remoteAddr = " + remoteAddr);
        String macAddress = GetMacAddress.getMacAddress(ipAddr);
        System.out.println("macAddress = " + macAddress);
        return "ok";
    }


    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


//    @GetMapping("ok")
//    public String ok (){
//        return "ok";
//    }
}
