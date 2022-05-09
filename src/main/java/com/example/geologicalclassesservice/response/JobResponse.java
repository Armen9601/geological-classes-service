package com.example.geologicalclassesservice.response;

import com.example.geologicalclassesservice.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {

    private Status status;

}
