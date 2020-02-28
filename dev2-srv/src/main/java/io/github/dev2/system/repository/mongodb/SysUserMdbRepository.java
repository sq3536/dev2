package io.github.dev2.system.repository.mongodb;

import io.github.dev2.system.domain.SysUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SysUserMdbRepository extends MongoRepository<SysUser,String>
{

}
