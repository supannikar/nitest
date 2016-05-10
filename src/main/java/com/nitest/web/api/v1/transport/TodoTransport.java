package com.nitest.web.api.v1.transport;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name = "TodoTransport")
public class TodoTransport {
    @ApiObjectField(name = "id", description = "id", required = false)
    @JsonProperty("id")
    private Long id;

    @ApiObjectField(name = "subject", description = "subject", required = false)
    @JsonProperty("subject")
    private String subject;

    @ApiObjectField(name = "detail", description = "detail", required = false)
    @JsonProperty("detail")
    private String detail;

    @ApiObjectField(name = "status", description = "status", required = false)
    @JsonProperty("status")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
