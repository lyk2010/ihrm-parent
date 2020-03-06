package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.utils.JwtUtils;
import com.ihrm.domain.system.response.ProfileResult;
import com.ihrm.domain.system.User;
import com.ihrm.system.service.PermissionService;
import com.ihrm.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(value = "系统用户服务",tags = {"用户操作相关接口"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sys")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PermissionService permissionService;

    /**
     * 分配角色
     */
    @ApiOperation(value = "分配角色",notes = "具有权限的用户可进行分配角色")
    @ApiResponse(code = 10000,message = "操作成功")
    @PutMapping("/user/assignRoles")
    public Result assignRoles(@ApiParam(name = "map",value = "参数",required = true)
                                  @RequestBody Map<String,Object> map){
        //1.获取到被分配的用户id
        String userId = (String)map.get("id");
        //2.获取到角色的id列表
        List<String> roleIds = (List<String>) map.get("roleIds");
        //3.调用service完成分配
        userService.assignRoles(userId,roleIds);
        return new Result(ResultCode.SUCCESS);
    }




    /**
     * 保存用户
     * @param user
     * @return
     */
    @ApiOperation(value = "保存用户",notes = "该接口用于保存用户")
    @PostMapping(value = "/user")
    public Result save(@ApiParam(name = "user",value = "实体类User",required = true)
                           @RequestBody User user){
        //1.设置保存的企业id（目前使用固定值1）
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        //2.调用service完成保存
        userService.add(user);
        //3.构造返回结果
        return new Result(ResultCode.SUCCESS);

    }

    /**
     * 根据id更新用户
     * @param id
     * @param user
     * @return
     */
    @PutMapping(value = "/user/{id}")
    public Result update(@PathVariable(value = "id") String id,
                         @RequestBody User user){
        user.setId(id);
        userService.update(user);
        return new Result(ResultCode.SUCCESS);

    }


    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @DeleteMapping(value = "/user/{id}",name = "API-USER-DELETE")
    public Result delete(@PathVariable(value = "id") String id){
        userService.delete(id);
        return new Result(ResultCode.SUCCESS);

    }


    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping(value = "/user/{id}")
    public Result findById(@PathVariable(value = "id") String id){
        User user = userService.findById(id);
        return new Result(ResultCode.SUCCESS,user);

    }


    /**
     * 查询所有用户
     * @return
     */
    @GetMapping(value = "/user")
    public Result findAll(int page, int size, @RequestParam Map map){
        //1.获取当前的企业id
        map.put("companyId",companyId);
        //2.完成查询
        Page<User> pageUser = userService.findAll(map, page, size);
        //3.构造返回结果
        PageResult<User> pageResult = new PageResult<>(pageUser.getTotalElements(),pageUser.getContent());
       return new Result(ResultCode.SUCCESS,pageResult);
    }



    /**
     * 用户登录
     *      1.通过service根据mobile查询用户
     *      2.比较password
     *      3.生成jwt信息
     */
    @PostMapping(value = "/login")
    public Result login(@RequestBody Map<String,String> loginMap) {
        String mobile = loginMap.get("mobile");
        String password = loginMap.get("password");
        try {
            //1.构造登录的令牌 UsernamePasswordToken
            //对登录密码进行加密
            password = new Md5Hash(password, mobile, 3).toString(); //三个参数分别为：密码 盐 加密次数
            UsernamePasswordToken uptoken = new UsernamePasswordToken(mobile,password);
            //2.获取subject
            Subject subject = SecurityUtils.getSubject();
            //3.调用subject.login方法，进入realm完成认证
            subject.login(uptoken);
            //4.获取sessionid
            String sessionId = (String) subject.getSession().getId();
            //5.构造返回信息
            return new Result(ResultCode.SUCCESS,sessionId);
        } catch (Exception e) {
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        }







//        User user = userService.findByMobile(mobile);
//        //登录失败
//        if (user == null || !user.getPassword().equals(password)) {
//            return new Result(ResultCode.MOBILEORPASSWORDERROR);
//        }else {
//            //登录成功
//            //api 权限字符串
//            StringBuilder sb = new StringBuilder();
//            //获取到所有可访问的API权限
//            for (Role role : user.getRoles()) {
//                for (Permission perm : role.getPermissions()) {
//                    if (perm.getType() == PermissionConstants.PY_API) {
//                        sb.append(perm.getCode()).append(",");
//                    }
//                }
//            }
//
//
//            Map<String,Object> map = new HashMap<>();
//            map.put("apis",sb.toString());//可访问的api权限字符串
//            map.put("companyId",user.getCompanyId());
//            map.put("companyName",user.getCompanyName());
//            String token = jwtUtils.createJwt(user.getId(), user.getUsername(), map);
//            return new Result(ResultCode.SUCCESS,token);
//        }

    }


    /**
     *用户登录成功后，获取用户信息
     *
     *      1.获取用户id
     *      2.根据用户id查询用户
     *      3.构建返回值对象
     *      4.响应数据
     */
    @PostMapping(value = "/profile")
    public Result profile(HttpServletRequest request) throws Exception{
        //获取到session中的安全数据
        Subject subject = SecurityUtils.getSubject();
        //1.subject获取所有的安全数据集合
        PrincipalCollection principals = subject.getPrincipals();
        //2.获取安全数据
        ProfileResult profileResult = (ProfileResult) principals.getPrimaryPrincipal();

//        /**
//         * 注意：前后端约定：前端请求微服务时需要添加头信息Authorization，内容为Bearer+空格+token
//         * 从请求头信息中获取token数据
//         *      1.获取请求头信息：名称 = Authorization
//         *      2.替换Bearer+空格
//         *      3.解析token
//         *      4.获取clamis
//         */
//        String authorization = request.getHeader("Authorization");
//        if (StringUtils.isEmpty(authorization)) {
//            throw new CommonException(ResultCode.UNAUTHENTICATED);
//        }
//        String token = authorization.replace("Bearer ","");
//        Claims claims = jwtUtils.parseJwt(token);
//        Claims claims =(Claims) request.getAttribute("user_claims");
//        String userId = claims.getId();
//        //获取用户信息
//        User user = userService.findById(userId);
//        //根据不同的用户级别获取用户权限
//        ProfileResult profileResult = null;
//
//        if ("user".equals(user.getLevel())) {
//            profileResult = new ProfileResult(user);
//        }else {
//            Map map = new HashMap();
//            if ("coAdmin".equals(user.getLevel())) {
//                map.put("enVisible","1");
//            }
//            List<Permission> list = permissionService.findAll(map);
//            profileResult = new ProfileResult(user,list);
//        }

        return new Result(ResultCode.SUCCESS,profileResult);
    }



}
