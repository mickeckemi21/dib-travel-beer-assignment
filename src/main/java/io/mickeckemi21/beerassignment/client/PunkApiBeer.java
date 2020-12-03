package io.mickeckemi21.beerassignment.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collection;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PunkApiBeer {

    private String name;
    private String description;
    private Method method;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Method {

        @JsonProperty("mash_temp")
        private Collection<MashTemp> mashTemp;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class MashTemp {

            private Temp temp;

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Temp {
                private Double value;
            }

        }

    }

}
