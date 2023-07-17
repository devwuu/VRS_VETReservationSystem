package com.web.vt.domain.reservationmanagement;

import com.web.vt.common.RestDocsTestSupport;
import com.web.vt.domain.common.enums.UsageStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static com.web.vt.common.RestDocsConfiguration.field;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReservationManagementAdminControllerTest extends RestDocsTestSupport {

    private final ZonedDateTime START = LocalDateTime.now().atZone(ZoneOffset.UTC);
    private final ZonedDateTime END = START.plusDays(1);

    @Test
    @DisplayName("새로운 예약 관리 정보를 등록합니다")
    public void save() throws Exception {
        ReservationManagementVO vo = new ReservationManagementVO()
                .status(UsageStatus.NOT_USE)
                .clinicId(352L)
                .startDateTime(START.toInstant())
                .endDateTime(END.toInstant());

        mvc.perform(post("/v1/admin/reservation-management/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(vo)))
                .andDo(docs.document(
                                requestFields(
                                        fieldWithPath("id").ignored(),
                                        fieldWithPath("clinicId").type(JsonFieldType.NUMBER).description("병원 id"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).attributes(field("constraints", "[ Y | N | D ]")).description("상태(사용, 미사용, 삭제)"),
                                        fieldWithPath("startDateTime").type(JsonFieldType.STRING).attributes(field("constraints", "YYYY-MM-DDTMM:mm:ss.sssZ")).description("예약시작일시").optional(),
                                        fieldWithPath("endDateTime").type(JsonFieldType.STRING).attributes(field("constraints", "YYYY-MM-DDTMM:mm:ss.sssZ")).description("예약종료일시").optional(),
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

}