package com.lyj.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    @JsonProperty("Country")
    private String Country;
    @JsonProperty("Province")
    private String Province;
    @JsonProperty("City")
    private String City;
    @JsonProperty("District")
    private String District;
}
