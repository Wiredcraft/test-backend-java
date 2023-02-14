package com.w.t.common.security;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.BearerHttpAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public JwtRealm shiroJwtRealm(){
        return new JwtRealm();
    }

    @Bean
    public DefaultSecurityManager securityManager(JwtRealm jwtRealm){
        DefaultSecurityManager defaultSecurityManager=new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(jwtRealm);
        // 关闭自带session
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        defaultSecurityManager.setSubjectDAO(subjectDAO);
        return defaultSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultSecurityManager defaultSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(defaultSecurityManager);

        Map<String, Filter> filterMap=new LinkedHashMap<>();
        filterMap.put("bearer",new BearerHttpAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        Map<String,String> filterRuleMap=new HashMap<>();

        //Swagger
        filterRuleMap.put("/swagger-ui.html","anon");
        filterRuleMap.put("/swagger-ui/*","anon");
        filterRuleMap.put("/swagger-resources/**","anon");
        filterRuleMap.put("/v2/api-docs","anon");
        filterRuleMap.put("/v3/api-docs","anon");
        filterRuleMap.put("/webjars/**","anon");

        //登录
        filterRuleMap.put("/auth/login","anon");

        //交换
        filterRuleMap.put("/e/**","anon");

        //bearer jwt
        filterRuleMap.put("/**","bearer");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return shiroFilterFactoryBean;
    }

}
