package ooproject.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ForestedEquator.class, name = "forestedEquator"),
        @JsonSubTypes.Type(value = CrawlingJungle.class, name = "crawlingJungle")
})
public interface PlantGrowingVariant {
    boolean isPreferredField(WorldMap map, Vector2d field);
}
