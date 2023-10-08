package com.makeid.demo.VO;

import lombok.Data;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-10-07
 */
@Data
public class ReturnVO {
    private String targetCodeId;

    private Long taskId;

    private String handler;

    private String opinion;

    private String currentUserId;
}
