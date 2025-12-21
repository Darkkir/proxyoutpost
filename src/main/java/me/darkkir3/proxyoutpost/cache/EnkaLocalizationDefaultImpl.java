package me.darkkir3.proxyoutpost.cache;

import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDateTime;
import java.util.Locale;

@Component
public class EnkaLocalizationDefaultImpl implements EnkaLocalization {

    private static final Logger log = LoggerFactory.getLogger(EnkaLocalizationDefaultImpl.class);
    private LocalDateTime lastFetchTime;
    private JsonNode rootNode;
    private final EnkaAPIConfiguration enkaAPIConfiguration;

    public EnkaLocalizationDefaultImpl(EnkaAPIConfiguration enkaAPIConfiguration) {
        this.enkaAPIConfiguration = enkaAPIConfiguration;
    }

    @Override
    public String getDefaultLanguage() {
        return Locale.getDefault().getLanguage();
    }

    @Override
    public String translate(String language, String key) {
        File localizationFile = new File(enkaAPIConfiguration.getConfigurationPath(), enkaAPIConfiguration.getLocsName());

        LocalDateTime currentTime = LocalDateTime.now();
        if(!localizationFile.exists() || currentTime.isAfter(lastFetchTime.plusHours(enkaAPIConfiguration.getRefreshTimeInHours()))) {
            try {
                this.saveLocalizationToFile();
                ObjectMapper objectMapper = new ObjectMapper();
                rootNode = objectMapper.readTree(localizationFile);
            } catch (URISyntaxException | MalformedURLException e) {
                log.error("Failed to read {} and save to {}...", enkaAPIConfiguration.getLocsUrl(), localizationFile.getPath());
                throw new RuntimeException(e);
            }
        }

        if(rootNode != null) {
            JsonNode languageNode = rootNode.get(language);
            if(languageNode != null) {
                return languageNode.get(key).asString(key);
            } else {
                log.error("Could not find language node for language {}", language);
            }
        }

        return null;
    }

    private void saveLocalizationToFile() throws URISyntaxException, MalformedURLException {
        File configFolder = new File(enkaAPIConfiguration.getConfigurationPath());
        if(!configFolder.exists()) {
            boolean configFolderCreated = configFolder.mkdir();
            if(!configFolderCreated) {
                log.error("Failed to create configuration folder {}...", configFolder.getPath());
            }
        }
        URL website = new URI(enkaAPIConfiguration.getLocsUrl()).toURL();
        try (FileOutputStream fos = new FileOutputStream(new File(configFolder, enkaAPIConfiguration.getLocsName()))) {
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            this.lastFetchTime = LocalDateTime.now();
        }
        catch(IOException e) {
            log.error("Failed to read locs.json file...");
        }
    }
}
