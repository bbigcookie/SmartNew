package UserMouleTest;

import com.dcits.user.constant.ResultStatus;
import com.dcits.user.util.JsonManager;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by gaojunc on 2017/12/24 17:40.
 * Created Reason:用户注册单元测试
 */
public class UserRegisterTest extends AbstractTest {

    //注册用户
    @Test
    public void testRegister() {
        //获取验证码
        String body = "{\n" +
                "   \"password\" : \"esb\",\n" +
                "   \"username\" : \"gaojun\"\n" +
                "}";
        HttpConnector httpConnector = new HttpConnector(TOMCAT_URL + "register/registerUsingUsername", body);

        String send = httpConnector.send();
        System.out.println("收到报文:" + send);
        ResultStatus resultStatus = JsonManager.fromJson(send, ResultStatus.class);
        Assert.assertEquals(resultStatus.getStatusCode(), "Success");
    }

}
