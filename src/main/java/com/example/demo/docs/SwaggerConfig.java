package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI restaurantOpenAPI() {
        // Server configuration
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Local Development Server");

        // Contact information
        Contact contact = new Contact();
        contact.setName("Restaurant API Team");
        contact.setEmail("support@restaurant.com");

        // API Information
        Info info = new Info()
                .title("Restaurant Management API")
                .version("1.0.0")
                .description("""
                        ## Restaurant Management System API Documentation
                        
                        Hệ thống quản lý nhà hàng bao gồm các chức năng:
                        - **Authentication**: Đăng nhập, đăng ký
                        - **Restaurant**: Quản lý nhà hàng
                        - **Category**: Quản lý danh mục món ăn
                        - **Menu**: Quản lý thực đơn
                        - **Order**: Quản lý đơn hàng
                        - **Promo**: Quản lý khuyến mãi
                        - **Rating Food**: Đánh giá món ăn
                        - **User**: Quản lý người dùng
                        
                        ### Authentication
                        Hầu hết các API yêu cầu JWT token. Sử dụng endpoint `/login/signin` để lấy token.
                        """)
                .contact(contact);

        // Security Scheme (JWT)
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Nhập JWT token từ /login/signin");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer))
                .components(new Components().addSecuritySchemes("Bearer Authentication", securityScheme))
                .addSecurityItem(securityRequirement);
    }
}
