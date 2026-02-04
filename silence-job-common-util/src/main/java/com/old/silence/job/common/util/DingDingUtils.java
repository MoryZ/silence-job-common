package com.old.silence.job.common.util;

import cn.hutool.core.util.StrUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.job.common.constant.SystemConstants;
import com.old.silence.job.log.SilenceJobLog;


public class DingDingUtils {

    public static final String atLabel = "@{0}";

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    /**
     * 组装 DingTalkRequest
     *
     * @param title 标题
     * @param text  内容
     * @return DingTalkRequest
     */
    public static String buildSendRequest(String title, String text, List<String> ats) {
        Map<String, Object> message = new HashMap<>();
        message.put("msgtype", "markdown");
        Map<String, Object> markdown = new HashMap<>();
        markdown.put("text", StrUtil.sub(getAtText(ats, text, atLabel), 0, 4000));
        markdown.put("title", title);
        message.put("markdown", markdown);

        // 处理提到的人
        Map<String, Object> at = new HashMap<>();
        at.put("atMobiles", ats);
        at.put("isAtAll", true);
        if (CollectionUtils.isNotEmpty(ats)) {
            at.put("isAtAll", ats.stream().map(String::toLowerCase).anyMatch(SystemConstants.AT_ALL::equals));
        }
        message.put("at", at);
        return JSON.toJSONString(message);
    }

    public static String getAtText(List<String> ats, String text, String atLabel) {
        if (CollectionUtils.isEmpty(ats)) {
            return text;
        }
        StringBuilder sb = new StringBuilder(text);
        sb.append(StrUtil.CRLF);
        ats.stream().filter(StrUtil::isNotBlank)
                .forEach(at -> sb.append(MessageFormat.format(atLabel, at)));
        return sb.toString();
    }

    /**
     * @param request DingTalkRequest
     */
    public static boolean sendMessage(String request, String url) {

        try {
            if (StrUtil.isBlank(url)) {
                return false;
            }

            // 发送POST请求
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(request))
                    .build();

            HttpResponse<String> response = HTTP_CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            String body = response.body();
            JSONObject bodyJson = JSON.parseObject(body);
            int errCode = bodyJson.getIntValue("errcode");
            if (errCode != 0) {
                SilenceJobLog.LOCAL.error("dingDingProcessNotify: 钉钉发送失败, 错误码:{}, 错误信息:{}", errCode, bodyJson.getString("errmsg"));
                return false;
            }
            return true;
        } catch (Exception e) {
            SilenceJobLog.LOCAL.error("dingDingProcessNotify", e);
        }

        return false;
    }


}
