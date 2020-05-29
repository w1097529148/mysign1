package com.mysign.service.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.mysign.service.Exception.MySignException;
import com.mysign.service.vo.ExceptionEnum;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/5/8 14:41
 */
@Component
public class AliSendSMS {

private static final String signName="三点考";

private static final String templateCode="SMS_189712491";
        public static String sendSMS(String phone)  {
            DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FyZwDAYfKw4NfKMtn8P", "IJjAxq28llKbZgePh9OgSox9f0HrnH");
            IAcsClient client = new DefaultAcsClient(profile);
            CommonRequest request = new CommonRequest();
            request.setMethod(MethodType.POST);
            request.setDomain("dysmsapi.aliyuncs.com");
            request.setVersion("2017-05-25");
            request.setAction("SendSms");
            request.putQueryParameter("RegionId", "cn-hangzhou");
            request.putQueryParameter("PhoneNumbers", phone);
            request.putQueryParameter("SignName", signName);
            request.putQueryParameter("TemplateCode", templateCode);
            String code = getCode();
            request.putQueryParameter("TemplateParam", "{\"code\":"+ code +"}");
            try {
                CommonResponse response = client.getCommonResponse(request);
                String data = response.getData();
                if (data.contains("Message\":\"OK")){
                    return code;
                }
                throw new MySignException(ExceptionEnum.CODE_GET_ERROR);
            } catch (Exception e) {
                throw new MySignException(ExceptionEnum.CODE_GET_ERROR);
            }
        }
        private static String getCode(){
            Integer[] integers={0,1,2,3,4,5,6,7,8,9};
            String code="";
            Random random = new Random();
            for (int i = 0; i < 4; i++) {
                code=integers[random.nextInt(10)].toString()+code;
            }
            return code;
        }


}
