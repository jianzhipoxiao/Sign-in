package com.jszx.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取客户端ip和mac地址
 *
 * @date 2023/06/27
 */
@Slf4j
public final class IpMacUtil {
    private IpMacUtil() {
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 参考文章： http://developer.51cto.com/art/201111/305181.htm
     * <p>
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * <p>
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     * <p>
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    @SuppressWarnings("all")
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取客户端IP，支持反向代理，如nginx，但不支持正向代理，比如客户端浏览器自己使用代理工具
     *
     * @param request 请求
     * @return 客户端IP
     */
    @SuppressWarnings("all")
    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 从类unix机器上获取mac地址
     *
     * @param ip ipv4地址
     * @return mac
     */
    public static String getMacInLinux(String ip) {
        String mac = "";
        if (ip != null) {
            try {
                Process process = Runtime.getRuntime().exec("arp " + ip);
                InputStreamReader ir = new InputStreamReader(process.getInputStream());
                LineNumberReader input = new LineNumberReader(ir);
                String line;
                StringBuffer s = new StringBuffer();
                while ((line = input.readLine()) != null) {
                    s.append(line);
                }
                mac = s.toString();
                if (StringUtils.isNotBlank(mac)) {
                    mac = mac.substring(mac.indexOf(":") - 2, mac.lastIndexOf(":") + 3);
                }
                return mac;
            } catch (Exception e) {
                log.error("---> 获取mac地址错误：{}", e.getMessage());
            }
        }
        return mac;
    }

    /**
     * 从windows机器上获取mac地址
     *
     * @param ip ipv4地址
     * @return mac
     */
    public static String getMacInWindows(final String ip) {
        String result;
        String[] cmd = {"cmd", "/c", "ping " + ip};
        String[] another = {"cmd", "/c", "ipconfig -all"};
        // 获取执行命令后的result
        String cmdResult = callCmd(cmd, another);
        // 从上一步的结果中获取mac地址
        result = filterMacAddress(ip, cmdResult, "-");
        return result;
    }

    /**
     * 命令执行
     *
     * @param cmd     命令
     * @param another another
     * @return 结果
     */
    public static String callCmd(String[] cmd, String[] another) {
        StringBuilder result = new StringBuilder();
        String line;
        try {
            Runtime rt = Runtime.getRuntime();
            // 执行第一个命令
            Process proc = rt.exec(cmd);
            proc.waitFor();
            // 执行第二个命令
            proc = rt.exec(another);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error("---> 执行获取mac地址命令错误：{}", e.getMessage());
        }
        return result.toString();
    }

    /**
     * 获取mac地址
     *
     * @param ip           ip地址
     * @param sourceString s
     * @param macSeparator s
     * @return mac
     */
    @SuppressWarnings("all")
    public static String filterMacAddress(final String ip, final String sourceString, final String macSeparator) {
        String result = "";
        String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(sourceString);
        while (matcher.find()) {
            result = matcher.group(1);
            // 因计算机多网卡问题，截取紧靠IP后的第一个mac地址
            //int num = sourceString.indexOf(ip) - sourceString.indexOf(": " + result + " ");
            //if (num > 0 && num < 300) {
            //break;
            //}
            if (sourceString.indexOf(ip) <= sourceString.lastIndexOf(matcher.group(1))) {
                break; //如果有多个IP,只匹配本IP对应的Mac.
            }
        }
        return result;
    }

    public static String commond(String cmd) throws IOException {
        Process process = Runtime.getRuntime().exec(cmd);
        InputStream inputStream = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    @SuppressWarnings("all")
    public static String getMacByIP(String ipAddress) {
        try {
            String result;
            // 获取当前系统
            String os = System.getProperty("os.name");
            if (os.toLowerCase().startsWith("win")) {
                result = commond("ping -n 2 " + ipAddress);
                if (result.contains("TTL") || result.contains("Ping")) {
                    result = commond("arp -a " + ipAddress);
                }
            } else {
                result = commond("ping -c 2 " + ipAddress);
                if (result.contains("TTL") || result.contains("ttl") || result.contains("statistics")) {
                    result = commond("arp -a " + ipAddress);
                }
            }
            String regExp = "([0-9A-Fa-f]{2})([-:][0-9A-Fa-f]{2}){5}";
            Pattern pattern = Pattern.compile(regExp);
            Matcher matcher = pattern.matcher(result);
            StringBuilder stringBuilder = new StringBuilder();
            while (matcher.find()) {
                String temp = matcher.group();
                stringBuilder.append(temp);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return null;
        }
    }
}

