package com.lotuas.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.sql.DataSource;

/**
 * 1、开启OAuth2.0授权服务机制，加载oauth2.0的配置类，并导入相关的Bean,如：
 *      1.1、AuthorizationServerEndpointsConfigure：用来配置授权authorization和令牌token的访问端点和令牌服务
 *      1.2、AuthorizationServerSecurityConfigure：用来配置令牌端点的安全约束设置
 *      1.3、ClientDetailsServiceConfigurer：用来配置客户端详情服务（ClientDetailsService），初始化客户端详情信息
 *    以上三个Bean会注入到配置类中AuthorizationServerConfigurer中，可以通过继承AuthorizationServerConfigurerAdapter，
 *    重写对应的config()方法来进行配置
 */
@EnableAuthorizationServer
@Configuration
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {


    @Autowired
    private DataSource dataSource;


    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 1.1、授权服务端点设置：
     *      AuthorizationEndpoint可以通过以下方式配置支持的授权类型AuthorizationServerEndpointsConfigurer。默认情况下，所有授权类型均受支持，除了密码（有关如何切换它的详细信息，请参见下文）。以下属性会影响授权类型：
     *      authenticationManager：通过注入密码授权被打开AuthenticationManager。
     *      userDetailsService：如果您注入UserDetailsService或者全局配置（例如a GlobalAuthenticationManagerConfigurer），则刷新令牌授权将包含对用户详细信息的检查，以确保该帐户仍然活动
     *      authorizationCodeServices：定义AuthorizationCodeServices授权代码授权的授权代码服务（实例）。
     *      implicitGrantService：在批准期间管理状态。
     *      tokenGranter：（TokenGranter完全控制授予和忽略上述其他属性）
     *
     *    默认oauth2.0授权服务端点：
     *      /oauth/authorize    授权端点,需要经过用户登录认证
     *      /oauth/token    令牌端点，不需要认证
     *      /oauth/confirm_access    用户批准授权的端点
     *      /oauth/error    用于渲染授权服务器的错误
     *      /oauth/check_token    资源服务器解码access token
     *      /oauth/check_token    当使用JWT的时候，暴露公钥的端点
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.authenticationManager(authenticationManager)
//                .tokenStore(memoryTokenStore())
//                .userDetailsService(userDetailsService);
        super.configure(endpoints);
    }


    /**
     * 2、令牌服务端点安全约束
     *      /oauth/token端点：
     *          这个如果配置支持allowFormAuthenticationForClients的，且url中有client_id和client_secret的会走ClientCredentialsTokenEndpointFilter来保护
 *              如果没有支持allowFormAuthenticationForClients或者有支持但是url中没有client_id和client_secret的，走basic认证保护
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").allowFormAuthenticationForClients();
    }





    /**
     * 3、配置客户端详情信息，可以配置多个客户端
     *      clientId：（必须）客户端id。
     *      secret：（对于可信任的客户端是必须的）客户端的私密信息。
     *      scope：客户端的作用域。如果scope未定义或者为空（默认值），则客户端作用域不受限制。
     *      authorizedGrantTypes：授权给客户端使用的权限类型。默认值为空。
     *      authorities：授权给客户端的权限（Spring普通的安全权限）。
     *
     *配置方式：
     *      3.1、内存中设置客户端信息inMemory()
     *      3.2、从数据源中获取客户端信息，默认的表结构中
     *      3.3、自定义通过clientId获取客户端信息
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        1、内存中设置客户端信息inMemory()，注意授权码授权时，必须设置好重定向地址
        clients.inMemory()
                .withClient("client1").secret("client1").authorizedGrantTypes("authorization_code")
                .scopes("client1_read_write").authorities("WEB_CLIENT").redirectUris("http://baidu.com")
                .and()
                .withClient("client2").secret("client2").authorizedGrantTypes("password")
                .scopes("client2_read_write").authorities("WEB_CLIENT").redirectUris("http://baidu.com");



        //2、从jdbc数据源中获取客户端信息，默认的表结构中
//        clients.jdbc(dataSource);
//        clients.withClientDetails(new JdbcClientDetailsService(dataSource));


//        3、自定义通过clientId获取客户端信息
//        clients.withClientDetails(new ClientDetailsService() {
//            @Override
//            public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
//
//                return null;
//            }
//        });

    }



    // 使用最基本的InMemoryTokenStore生成token
    @Bean
    public TokenStore memoryTokenStore() {
        return new InMemoryTokenStore();
    }

}
