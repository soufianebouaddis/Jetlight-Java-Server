package org.os.controller;

import org.os.annotations.Controller;
import org.os.annotations.Inject;
import org.os.dto.LoginDto;
import org.os.dto.RegisterDto;
import org.os.model.Tenant;
import org.os.service.TenantService;
import org.os.util.JsonUtil;
import org.os.util.JwtUtil;

@Controller
public class AuthController {
    @Inject
    private TenantService tenantService;

    public String register(String registerRequestBody) throws Exception {
        RegisterDto json = JsonUtil.fromJsonToRegister(registerRequestBody);
        var user = tenantService.save(new Tenant.Builder()
                .id()
                .name(json.getName())
                .username(json.getUsername())
                .password(json.getPassword())
                .organization(json.getOrganization())
                .role(json.getRole()).build());
        return JsonUtil.toJson(user);
    }

    public String login(String loginRequestBody) {
        LoginDto json = JsonUtil.fromJson(loginRequestBody, LoginDto.class);
        if (tenantService.validateUser(json.getUsername(), json.getPassword())) {
            Tenant tenant = tenantService.getByUsername(json.getUsername());
            String token = JwtUtil.generate(tenant.getUsername(), tenant.getRole());
            return JsonUtil.tokenToJson("200","Authenticated",token);
        }
        return JsonUtil.tokenToJson("500","","");
    }
}
