package ooproject.interfaces;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ooproject.model.CrawlingJungle;
import ooproject.model.ForestedEquator;
import ooproject.model.Vector2d;
import ooproject.model.WorldMap;

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
