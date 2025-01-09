package com.migros.courier_tracker.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierResponse {
    //TODO: is it necessary ? think about it
    private Long id;
    private String name;
}
