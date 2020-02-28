package io.github.dev2.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.dev2.security.core.AuthenticationUser;
import io.github.dev2.system.domain.SysUser;
import io.github.dev2.system.mapper.SysUserMapper;
import io.github.dev2.system.service.SysUserService;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 实体[SysUser] 服务对象接口实现
 */
@Service
public class SysUserMdbServiceImpl  implements SysUserService
{



	public AuthenticationUser getByUsername(String username){

		if(StringUtils.isEmpty(username))
		throw new UsernameNotFoundException("用户名为空");
		QueryWrapper<SysUser> conds=new QueryWrapper<SysUser>();
		String[] data=username.split("[|]");
		String loginname="";
		String domains="";
		if(data.length>0)
		loginname=data[0].trim();
		if(data.length>1)
		domains=data[1].trim();
		if(!StringUtils.isEmpty(loginname))
		conds.eq("loginname",loginname);
		if(!StringUtils.isEmpty(domains))
		conds.eq("domains",domains);
		SysUser user = this.getOne(conds);
		if (user == null)
		{
		throw new UsernameNotFoundException("用户" + username + "未找到");
		}
		else
		{
		user.setUsername(username);
		return createUserDetails(user);
		}
    }
    public void resetByUsername(String username)
    {

    }

    public  AuthenticationUser createUserDetails(SysUser user) {
		AuthenticationUser userdatail = new AuthenticationUser();
		BeanCopier copier= BeanCopier.create(SysUser.class,AuthenticationUser.class,false);
		copier.copy(user,userdatail,null);
		return userdatail;
		}
    }