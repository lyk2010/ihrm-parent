package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.Role;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys")
@CrossOrigin
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;


    /**
     * 分配权限
     */
    @PutMapping("/role/assignRoles")
    public Result assignRoles(@RequestBody Map<String,Object> map){
        //1.获取到被分配的角色的id
        String roleId = (String)map.get("id");
        //2.获取到权限的id列表
        List<String> permIds = (List<String>) map.get("permIds");
        //3.调用service完成权限分配
        roleService.assignPerms(roleId,permIds);
        return new Result(ResultCode.SUCCESS);
    }




    //添加角色
    @PostMapping(value = "/role")
    public Result add(@RequestBody Role role) throws Exception {
        role.setCompanyId(companyId);
        roleService.add(role);
        return Result.SUCCESS();
    }

    //更新角色
    @PutMapping("/role/{id}")
    public Result update(@PathVariable(name = "id") String id,@RequestBody Role role) throws Exception {
        roleService.update(role);
        return Result.SUCCESS();
    }


    //删除角色
    @DeleteMapping("/role/{id}")
    public Result delete(@PathVariable String id) throws Exception {
        roleService.deleteById(id);
        return Result.SUCCESS();
    }



}
