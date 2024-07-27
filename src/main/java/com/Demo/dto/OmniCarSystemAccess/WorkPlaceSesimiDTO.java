package com.Demo.dto.OmniCarSystemAccess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlaceSesimiDTO {
    private Long id;
    private boolean contentStore;
    private boolean qw90;
    private boolean vistaAccess;
}
