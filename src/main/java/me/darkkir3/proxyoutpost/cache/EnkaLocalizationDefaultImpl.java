package me.darkkir3.proxyoutpost.cache;

import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class EnkaLocalizationDefaultImpl implements EnkaLocalization {

    private static final Logger log = LoggerFactory.getLogger(EnkaLocalizationDefaultImpl.class);
    private LocalDateTime lastFetchTime;
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
        return "";
    }

    private void saveLocalizationToFile() throws URISyntaxException, MalformedURLException {
        URL website = new URI(enkaAPIConfiguration.getLocsUrl()).toURL();
        try (FileOutputStream fos = new FileOutputStream(enkaAPIConfiguration.getLocsName())) {
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
        catch(IOException e) {
            log.error("Failed to read locs.json file");
        }
    }
}
