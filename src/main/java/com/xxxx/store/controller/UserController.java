package com.xxxx.store.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.xxxx.store.controller.exception.FileEmptyException;
import com.xxxx.store.entity.User;
import com.xxxx.store.mapper.UserMapper;
import com.xxxx.store.service.exception.TokenException;
import com.xxxx.store.service.impl.UserServiceImpl;
import com.xxxx.store.token.TokenUtil;
import com.xxxx.store.util.JsonResult;
import com.xxxx.store.util.Test;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")

public class UserController extends BaseController{
    @Autowired
    UserServiceImpl userService;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    UserMapper userMapper;


    @ApiOperation(value = "注册")
    @PostMapping("/reg")
    public JsonResult<Void> reg(@RequestBody User user){

//        System.out.println(user.getUser_Id());
//        System.out.println(user.getUser_Pwd());
        userService.reg(user);
        return new JsonResult<Void>(OK);
    }


    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public JsonResult<User> login(@RequestBody User user) throws UnsupportedEncodingException {
//        User user = new User();
//        user.setUser_Id();
//        user.setUser_Pwd(User_Pwd);
        User data = userService.login(user.getUser_Id(),user.getUser_Pwd());
        TokenUtil tokenUtil = new TokenUtil();
        String token = tokenUtil.createToken(data);
        return new JsonResult<User>(OK,token,data);
    }

    @ApiOperation(value = "修改密码")
    @PostMapping("/change")
    public JsonResult<User> UpdatePassWord(@RequestBody User user, @RequestHeader("token") String token){

        DecodedJWT jwt = TokenUtil.getClaimsFromToken(token);
        String Id = jwt.getClaim("id").asString();
        System.out.println(Id);
        User data = userService.update(user.getOldUser_Pwd(), user.getNewUser_Pwd(), Id);
        return new JsonResult<User>(OK,data);
    }

    @ApiOperation(value = "注销账号")
    @PostMapping("/cancellation")
    public JsonResult<Void> cancellation(@RequestHeader("token") String token){
//        System.out.println(token);
        DecodedJWT jwt = TokenUtil.getClaimsFromToken(token);
        String Id = jwt.getClaim("id").asString();
        userService.cancellation(Integer.valueOf(Id));
        JsonResult jsonResult = new JsonResult();
        jsonResult.setMessage("注销成功");
        jsonResult.setState(OK);
        return jsonResult;
    }

    /** 头像文件大小的上限值(10MB) */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
//    /** 允许上传的头像的文件类型 */
//    public static final List<String> AVATAR_TYPES = new ArrayList<String>();
//
//    /** 初始化允许上传的头像的文件类型 */
//    static {
//        AVATAR_TYPES.add("user_img/jpeg");
//        AVATAR_TYPES.add("user_img/png");
//        AVATAR_TYPES.add("user_img/bmp");
//        AVATAR_TYPES.add("user_img/jpg");
//    }

    @ApiOperation(value = "上传头像")
    @PostMapping("/change_avatar")
    public JsonResult<User> changeAvatar(@RequestParam("file") MultipartFile file, @RequestHeader("token") String token) {

        if (token==null || "".equals(token)){
            throw new TokenException("token验证失败！");
        }
        // 判断上传的文件是否为空
        if (file.isEmpty()) {
            // 是：抛出异常
            throw new FileEmptyException("上传的头像文件不允许为空");
        }

        // 判断上传的文件大小是否超出限制值
        if (file.getSize() > AVATAR_MAX_SIZE) { // getSize()：返回文件的大小，以字节为单位
            // 是：抛出异常
            throw new FileEmptyException("不允许上传超过" + (AVATAR_MAX_SIZE / 1024) + "KB的头像文件");
        }

        // 判断上传的文件类型是否超出限制
//        String contentType = file.getContentType();
//        // boolean contains(Object o)：当前列表a若包含某元素，返回结果为true；若不包含该元素，返回结果为false
//        if (!AVATAR_TYPES.contains(contentType)) {
//            // 是：抛出异常
//            throw new FileEmptyException("不支持使用该类型的文件作为头像，允许的文件类型：" + AVATAR_TYPES);
//        }

//        // 获取当前项目的绝对磁盘路径
        String path="src\\main\\resources\\static\\user_img";
        File mkdirs=new File(path);
        if(!mkdirs.exists()){
            mkdirs.mkdirs();
        }

        String originalFileName = file.getOriginalFilename();
        System.out.println("原始文件名称" + originalFileName);
        //获取文件名，不包含类型
        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        //获取文件类型，以最后一个‘.’为标识
        // 保存的头像文件的文件名
        String suffix = "";
        String originalFilename = file.getOriginalFilename();
        int beginIndex = originalFilename.lastIndexOf(".");
        if (beginIndex > 0) {
            //获取文件类型
            suffix = originalFilename.substring(beginIndex);
        }
        System.out.println(suffix);
        String filename = UUID.randomUUID().toString() + name + suffix;


        // 创建文件对象，表示保存的头像文件
        File dest =new File( System.getProperties().getProperty("user.dir")+"\\"+path+"\\"+filename);


        // 执行保存头像文件
        System.out.println(file.getSize()/1024);
        System.out.println(dest);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e);
        }

        // 头像路径
        String avatar = "static\\user_img\\" + filename;
        // 从Session中获取uid和username
        DecodedJWT jwt = TokenUtil.getClaimsFromToken(token);
        String Id = jwt.getClaim("id").asString();
        String username = jwt.getClaim("username").asString();
        // 将头像写入到数据库中
        User user = userService.changeAvatar(Id, username, avatar);

        // 返回成功头像路径
        return new JsonResult<User>(OK, user);
    }


    @PostMapping("/token")
    public String token() throws UnsupportedEncodingException {
        TokenUtil tokenUtil = new TokenUtil();
        User user = new User();
        user.setUser_Id("123");
        user.setId(1);
        String token = tokenUtil.createToken(user);
        return token;
    }


    @PostMapping("/test")
    public List<User> test(){
        List<User> user = userMapper.findAll();
        System.out.println(user);
        return user;
    }

    @PostMapping("/qwe")
    public String test1(){
        Test test = new Test(1,"!2");
        return "rrrtet";
    }
}
