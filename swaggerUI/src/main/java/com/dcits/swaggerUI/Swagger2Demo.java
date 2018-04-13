package com.dcits.swaggerUI;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class Swagger2Demo {
    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String", name = "id", value = "id", required = true, paramType = "path"),
            @ApiImplicitParam(dataType = "UserBean", name = "user", value = "用户信息", required = true, paramType="body")
    })
    @RequestMapping(value = "/user/{id}",method = RequestMethod.POST)
    public Swagger2DemoBean insert(@PathVariable Long id, @RequestBody Swagger2DemoBean user){

        System.out.println("id:"+id+", user:"+user);
        user.setId(id);

        return user;
    }

    @ApiOperation(value="获取指定id用户详细信息", notes="根据user的id来获取用户详细信息")
    @ApiImplicitParam(name = "id",value = "用户id", dataType = "String", paramType = "path")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Swagger2DemoBean getUser(@PathVariable Long id){

        Swagger2DemoBean user = new Swagger2DemoBean();
        user.setId(id);
        user.setPassword("abc");
        user.setUsername("12345");
        return user;
    }

    @ApiOperation(value="获取所有用户详细信息", notes="获取用户列表信息")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<Swagger2DemoBean> getUserList(){

        List<Swagger2DemoBean> list = new ArrayList<Swagger2DemoBean>();
        Swagger2DemoBean user = new Swagger2DemoBean();
        user.setId(15L);
        user.setPassword("ricky");
        user.setUsername("root");

        list.add(user);

        return list;
    }

    @ApiIgnore
    @ApiOperation(value="删除指定id用户", notes="根据id来删除用户信息")
    @ApiImplicitParam(name = "id",value = "用户id", dataType = "String", paramType = "path")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id){
        System.out.println("delete user:"+id);
        return "OK";
    }
}

@ApiModel("UserBean")
class Swagger2DemoBean{
    @ApiModelProperty(hidden = true)
    private Long id;
    @ApiModelProperty(name="password", value="密码", required=true,dataType = "java.lang.String")
    private String password;
    @ApiModelProperty(name="name", value="用户名", required=true,dataType = "java.lang.String")
    private String username;
    private int age;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
