package com.Demo.dto.OmniCarSystemAccess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrafficReportOminiStoreDTO {
    private Long id;
    private boolean cdsid;
    private boolean ominiVision;
    private boolean salesCloud;
}
