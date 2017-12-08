package com.xxd.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

/**java域登录验证
 *
 * @author gongzhifei
 * @date 2017/7/11
 */
@Component
public class DomainLogin {

    private static Logger logger = LoggerFactory.getLogger(DomainLogin.class);

    @Value("${xxd.domain.ip}")
    private String domainIp;
    @Value("${xxd.domain.port}")
    private String domainPort;


    /**
     * 使用java连接AD域
     * @date 2017-07-11
     * @throws
     * @param username 用户名
     * @param password 密码
     * @return Integer 1 success 、0 false 、 -1 exception
     */
    public Integer connectAD(String username,String password) {
        DirContext ctx=null;
        int isLogin = 0;
        Hashtable<String,String> HashEnv = new Hashtable<String,String>();
        HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); // LDAP访问安全级别(none,simple,strong)
        HashEnv.put(Context.SECURITY_PRINCIPAL, username!=null?username:""); //AD的用户名
        HashEnv.put(Context.SECURITY_CREDENTIALS, password!=null?password:""); //AD的密码
        HashEnv.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory"); //LDAP工厂类
        HashEnv.put("com.sun.jndi.ldap.connect.timeout","3000");//连接超时设置为3秒
        HashEnv.put(Context.PROVIDER_URL," ldap://" + domainIp + ":" + domainPort);//默认端口389
        try {
            if(password!=null&&!password.equals("")){
                ctx = new InitialDirContext(HashEnv);//初始化上下文
                logger.debug("身份验证成功!");
                isLogin = 1;
            }else{
                logger.debug("身份验证失败!");
                isLogin = 0;//没有输入密码属于身份失败
            }
        } catch (AuthenticationException e) {
            logger.debug("身份验证失败!",e);
            isLogin = 0;
        } catch (javax.naming.CommunicationException e) {
            logger.debug("AD域链接失败!",e);
            isLogin = -1;
        } catch (Exception e) {
            logger.error("身份验证未知异常!",e);
            isLogin = -1;
        } finally{
            if(null!=ctx){
                try {
                    ctx.close();
                    ctx=null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return isLogin;
    }

    public String getDomainIp() {
        return domainIp;
    }

    public void setDomainIp(String domainIp) {
        this.domainIp = domainIp;
    }

    public String getDomainPort() {
        return domainPort;
    }

    public void setDomainPort(String domainPort) {
        this.domainPort = domainPort;
    }
}

