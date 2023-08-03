package com.web.vt.security;

import com.web.vt.common.RestDocsTestSupport;
import com.web.vt.domain.employee.EmployeeVO;
import com.web.vt.domain.user.AdminVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecurityTest extends RestDocsTestSupport {

    @BeforeEach
    public void setup(WebApplicationContext context){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test @DisplayName("시스템 관리자로 로그인합니다.")
    public void adminLogin() throws Exception {
        AdminVO vo = new AdminVO()
                .id("test")
                .password("1234");

        mvc.perform(post("/admin/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(vo)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("병원 관리자로 로그인합니다")
    public void employeeLogin() throws Exception {
        EmployeeVO vo = new EmployeeVO()
                .id("test")
                .password("1234");

        mvc.perform(post("/client/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(vo)))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
