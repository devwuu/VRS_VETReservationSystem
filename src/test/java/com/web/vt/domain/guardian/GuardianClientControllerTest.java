package com.web.vt.domain.guardian;

import com.web.vt.common.ControllerTestSupporter;
import com.web.vt.domain.common.enums.UsageStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithUserDetails;

import static com.web.vt.common.RestDocsConfiguration.field;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(userDetailsServiceBeanName = "employeeDetailService", value = "test")
class GuardianClientControllerTest extends ControllerTestSupporter {

    @Test
    @DisplayName("신규 보호자를 등록합니다")
    public void save() throws Exception {

        GuardianVO vo = new GuardianVO().name("달이 언니").contact("01011112222").status(UsageStatus.USE);

        mvc.perform(post("/v1/client/guardian/save")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(vo)))
                .andDo(docs.document(
                        requestFields(
                                fieldWithPath("id").ignored(),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("보호자 이름"),
                                fieldWithPath("contact").type(JsonFieldType.STRING).description("보호자 연락처"),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("보호자 주소").optional(),
                                fieldWithPath("status").type(JsonFieldType.STRING).attributes(field("constraints", "[ Use | NotUse | Deleted ]")).description("상태"),
                                fieldWithPath("remark").type(JsonFieldType.STRING).description("비고").optional(),
                                fieldWithPath("createdAt").ignored(),
                                fieldWithPath("updatedAt").ignored(),
                                fieldWithPath("createBy").ignored(),
                                fieldWithPath("updatedBy").ignored()
                        )
                    )
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test @DisplayName("보호자 정보를 수정합니다")
    public void update() throws Exception {
        GuardianVO vo = new GuardianVO().id(152L).name("달이 언니").contact("01111112222").status(UsageStatus.USE);

        mvc.perform(post("/v1/client/guardian/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(vo)))
                .andDo(docs.document(
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("보호자 id"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("보호자 이름").optional(),
                                        fieldWithPath("contact").type(JsonFieldType.STRING).description("보호자 연락처").optional(),
                                        fieldWithPath("address").type(JsonFieldType.STRING).description("보호자 주소").optional(),
                                        fieldWithPath("status").type(JsonFieldType.STRING).attributes(field("constraints", "[ Use | NotUse | Deleted ]")).description("상태"),
                                        fieldWithPath("remark").type(JsonFieldType.STRING).description("비고").optional(),
                                        fieldWithPath("createdAt").ignored(),
                                        fieldWithPath("updatedAt").ignored(),
                                        fieldWithPath("createBy").ignored(),
                                        fieldWithPath("updatedBy").ignored()
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("등록된 사용중인 보호자 정보를 조회합니다")
    public void find() throws Exception {
        String id = "302";
        mvc.perform(RestDocumentationRequestBuilders.get("/v1/client/guardian/{id}", id))
                .andDo(docs.document(
                        pathParameters(
                                parameterWithName("id").attributes(field("type", "Number")).description("보호자 id")
                        )
                    )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @DisplayName("보호자를 삭제 처리 합니다")
    public void delete() throws Exception {
        GuardianVO vo = new GuardianVO().id(402L);
        mvc.perform(post("/v1/client/guardian/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(vo)))
                .andDo(docs.document(
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("보호자 id"),
                                        fieldWithPath("name").ignored(),
                                        fieldWithPath("contact").ignored(),
                                        fieldWithPath("address").ignored(),
                                        fieldWithPath("status").ignored(),
                                        fieldWithPath("remark").ignored(),
                                        fieldWithPath("createdAt").ignored(),
                                        fieldWithPath("updatedAt").ignored(),
                                        fieldWithPath("createBy").ignored(),
                                        fieldWithPath("updatedBy").ignored()
                                )
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

}