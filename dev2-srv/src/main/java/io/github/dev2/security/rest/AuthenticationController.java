package io.github.dev2.security.rest;

import io.github.dev2.security.core.AuthTokenUtil;
import io.github.dev2.security.core.AuthenticationInfo;
import io.github.dev2.security.core.AuthenticationUser;
import io.github.dev2.security.core.AuthorizationLogin;
import io.github.dev2.util.errors.BadRequestAlertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AuthenticationController
{
    @Value("${dev2.auth.pwencry:false}")
    private boolean pwencry;

    @Value("${dev2.jwt.header:Authorization}")
    private String tokenHeader;

    @Autowired
    private AuthTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("AuthenticationUserService")
    private UserDetailsService userDetailsService;



    @PostMapping(value = "${dev2.auth.path:login/login}")
    public ResponseEntity<AuthenticationInfo> login(@Validated @RequestBody AuthorizationLogin authorizationLogin){
        final AuthenticationUser authuserdetail = (AuthenticationUser) userDetailsService.loadUserByUsername(authorizationLogin.getUsername());
        String password=authorizationLogin.getPassword();
        if(pwencry)
            password= DigestUtils.md5DigestAsHex(authorizationLogin.getPassword().getBytes());
        if(!authuserdetail.getPassword().equals( password )){
            throw new BadRequestAlertException("用户名密码错误",null,null);
        }
        // 生成令牌
        final String token = jwtTokenUtil.generateToken(authuserdetail);
        // 返回 token
        return ResponseEntity.ok().body(new AuthenticationInfo(token,authuserdetail));
    }

    @GetMapping(value = "${dev2.auth.account:login/account}")
    public ResponseEntity<AuthenticationUser> getUserInfo(){
        UserDetails userDetails = (UserDetails)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthenticationUser authuserdetail;
        if(userDetails==null){
            throw new BadRequestAlertException("未能获取用户信息",null,null);
        }
        else if(userDetails instanceof AuthenticationUser ) {
            authuserdetail= (AuthenticationUser)userDetails;
        }
        else {
            authuserdetail= (AuthenticationUser)userDetailsService.loadUserByUsername(userDetails.getUsername());
        }
        return ResponseEntity.ok().body(authuserdetail);
    }
}
