package me.darkkir3.proxyoutpost.model.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.darkkir3.proxyoutpost.model.db.PlayerTitleArgs;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * represents a title entry in titles.json
 */
public class TitleOutput {

    private static final Logger log = LoggerFactory.getLogger(TitleOutput.class);
    /**
     * the non-full title text
     */
    @JsonProperty("TitleText")
    public String titleText;

    /**
     * first highlight color
     */
    @JsonProperty("ColorA")
    public String colorA;

    /**
     * second hightlight color
     */
    @JsonProperty("ColorB")
    public String colorB;

    /**
     * alternative titles with longer text
     */
    @JsonProperty("Variants")
    public Map<Long, String> variants;

    /** determine and return which title to display
     * @param title the title of that player
     * @param fullTitle the full title of that player
     * @param args values to fill the title with (e.g. a score)
     * @return Either the title or the full title
     */
    public String getTranslatedTitleTextBasedOnProfile(Long title, Long fullTitle, List<PlayerTitleArgs> args) {
        String titleToUse = this.titleText;

        if(fullTitle != null && variants != null) {
            String variant = variants.get(fullTitle);
            if(variant != null) {
                titleToUse = variant;
            }
        }

        if(!StringUtils.isBlank(titleToUse)) {
            try {
                //explicitly pass a string array instead of an object list in order to avoid square brackets
                List<String> argsToFormat = new ArrayList<>();
                args.forEach(t -> argsToFormat.add(String.valueOf(t)));
                titleToUse = MessageFormat.format(titleToUse, argsToFormat.toArray());
            }
            catch(IllegalArgumentException e) {
                log.error("Failed to format title {} with title id {} and full title id {}",
                        titleToUse, title, fullTitle, e);
            }
        }

        return  titleToUse;
    }

    @JsonIgnore
    public String getColorA() {
        return colorA;
    }

    @JsonIgnore
    public String getColorB() {
        return colorB;
    }
}
