package UserMouleTest;

import com.dcits.user.constant.ResultStatus;
import com.dcits.user.util.JsonManager;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by gaojunc on 2018/3/14 13:56.
 * Created Reason:用户修改密码单元测试
 */
public class UserModifyTest extends AbstractTest {

    @Test
    public void modifyest() {
        String body = "{\"nickName\":\"\",\"password\":\"eeeee\",\"phoneNum\":\"\",\"username\":\"gaojun\"}";
        HttpConnector httpConnector = new HttpConnector(TOMCAT_URL + "/update/password", body);
        String send = httpConnector.send();
        System.out.println("收到报文:" + send);
        ResultStatus resultStatus = JsonManager.fromJson(send, ResultStatus.class);
        Assert.assertEquals(resultStatus.getStatusCode(), "Success");
    }
}
