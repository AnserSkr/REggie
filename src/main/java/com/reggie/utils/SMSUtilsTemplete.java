// This file is auto-generated, don't edit it. Thanks.
package com.reggie.utils;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;

import static com.aliyun.teautil.Common.assertAsString;

public class SMSUtilsTemplete {

    /**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    // public static void main(String[] args_) throws Exception {
    public static void sendMessage() throws Exception {
        Client client = SMSUtilsTemplete.createClient("accessKeyId", "accessKeySecret");
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                //短信签名
                .setSignName("阿里云短信测试")
                //短信模板Code
                .setTemplateCode("SMS_154950909")
                //绑定测试手机号
                .setPhoneNumbers("15646548133")
                //短信模板变量以及实际值
                .setTemplateParam("{\"code\":\"1234\"}");
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtime);
            System.out.println("短信发送成功");
        } catch (TeaException error) {
            // 如有需要，请打印 error
            assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            assertAsString(error.message);
        }
    }
}
