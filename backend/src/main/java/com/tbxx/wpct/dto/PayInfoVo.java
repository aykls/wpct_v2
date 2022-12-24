package com.tbxx.wpct.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayInfoVo {
    String villageName;
    String buildNo;
    String name;
    String payStatus;
    LocalDateTime payBeginTime;
    LocalDateTime payEndTime;

}
