package com.wiredcraft.test.user.auth;

/**
 * 用于终端用户访问，一般设计成token+3方扫码形式
 * 用户通过3方扫码登录后拿到token，再通过角色访问控制来限制资源的访问
 *
 * 企业级控制一般需要前端和后端配合来实现控制，设计如下
 * 资源级别：菜单，页面，按钮
 * 角色和资源绑定，用户和角色绑定
 */
public class TokenService {
}
