package UserMouleTest;

import com.dcits.user.constant.ResultStatus;
import com.dcits.user.util.JsonManager;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by gaojunc on 2017/12/30 18:29.
 * Created Reason: 用户登录单元测试
 */
public class UserloginTest extends AbstractTest {

    @Test
    public void loginTest() {
        String body = "{\"password\":\"esb\",\"phoneNum\":\"\",\"username\":\"gaojun\"}";
        HttpConnector httpConnector = new HttpConnector(TOMCAT_URL + "/login/dologin", body);
        String send = httpConnector.send();
        System.out.println("收到报文:" + send);
        ResultStatus resultStatus = JsonManager.fromJson(send, ResultStatus.class);
        Assert.assertEquals(resultStatus.getStatusCode(), "Success");
    }

}
