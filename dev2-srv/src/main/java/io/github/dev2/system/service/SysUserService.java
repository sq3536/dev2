package io.github.dev2.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.dev2.security.core.AuthenticationUser;
import io.github.dev2.system.domain.SysUser;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 * 实体[SysUser] 服务对象接口实现
 */
public  interface SysUserService extends IService<SysUser>
{

    @Cacheable( value="dev2_users",key = "'getByUsername:'+#p0")
    AuthenticationUser getByUsername(String username);
    @CacheEvict( value="dev2_users",key = "'getByUsername:'+#p0")
    void resetByUsername(String username);

    AuthenticationUser createUserDetails(SysUser user);

}