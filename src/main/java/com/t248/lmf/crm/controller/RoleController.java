package com.t248.lmf.crm.controller;

import com.t248.lmf.crm.entity.Right;
import com.t248.lmf.crm.entity.Role;
import com.t248.lmf.crm.entity.SysRoleRight;
import com.t248.lmf.crm.service.IRoleService;
import com.t248.lmf.crm.service.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RoleController {
    @Resource
    private IRoleService roleService;

    @Resource
    private IUserService userService;

    @RequestMapping("/role/json")
    @ResponseBody
    public Map findAllRoles(){
        List<Role> roles = roleService.findAllRoles();
        Map map = new HashMap();
        map.put("roles",roles);
        return map;
    }

    @RequestMapping("/role/del")
    @ResponseBody
    public Map delrole(Long roleId){
        Map<String,String> map = new HashMap<String,String>();
        try{
            System.out.println("\n\n"+roleId+"\n\n");
            roleService.deleRole(roleId);
            map.put("delResult","true");
        }catch(Exception e){
            e.printStackTrace();
            map.put("delResult","false");
        }
        return map;
    }

    @RequestMapping("/role/list")
    public String list(Model model,
                       @RequestParam(required = false) String roleName,
                       @RequestParam(required = false,defaultValue = "1") int pageIndex
    ){
        Sort sort = Sort.by(Sort.Direction.ASC,"roleId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);

        Page<Role> rolePage = roleService.findRoles(roleName,pageable);
        model.addAttribute("rolePage",rolePage);
        model.addAttribute("roleName",roleName);
        return "role/list";
    }

    @RequestMapping("role/save")
    public String save(Model model,String[] rightId,Role role){
//        roleService.saveRole(role);
        if(role.getRoleId()!=null){
            roleService.delSysRoleRight(role.getRoleId());
        }
        List<SysRoleRight> roleRights = new ArrayList<>();
        for (int i=0;i<rightId.length;i++){
            SysRoleRight sysRoleRight = new SysRoleRight();
            sysRoleRight.setRole(role);
            sysRoleRight.setRight(new Right(rightId[i]));
            roleRights.add(sysRoleRight);
            System.out.println(rightId[i]);
        }
        role.getSysRoleRights().addAll(roleRights);
        roleService.saveRole(role);
        return "redirect:/role/list";
    }

    @RequestMapping("role/add")
    public String add(Model model){
        return "role/add";
    }


    @RequestMapping("role/edit")
    public String edit(Model model,Long roleId){
        Role role = roleService.getRoleByroleId(roleId);
        model.addAttribute("role",role);
        Map<String,String> rigMap = new HashMap<>();
        for (SysRoleRight sysRoleRight:role.getSysRoleRights()){
            rigMap.put(sysRoleRight.getRight().getRightCode(),sysRoleRight.getRight().getRightCode());
        }
        model.addAttribute("rigMap",rigMap);
        return "role/edit";
    }

}
