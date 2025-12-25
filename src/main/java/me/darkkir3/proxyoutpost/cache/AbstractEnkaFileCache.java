package me.darkkir3.proxyoutpost.cache;

import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDateTime;

/**
 * represents a JSON mapping file from API-docs/store/zzz/
 */
public abstract class AbstractEnkaFileCache {

    private static final Logger log = LoggerFactory.getLogger(AbstractEnkaFileCache.class);

    /**
     * last time we tried to grab the store file from GitHub
     */
    private LocalDateTime lastFetchTime;

    /**
     * root node of the locs.JSON file
     */
    private JsonNode rootNode;

    /**
     * the enka api configuration
     */
    protected final EnkaAPIConfiguration enkaAPIConfiguration;

    /**
     * the spring cache manager to manually evict the cache
     * associated with this
     */
    private final CacheManager cacheManager;

    protected AbstractEnkaFileCache(EnkaAPIConfiguration enkaAPIConfiguration, CacheManager cacheManager) {
        this.enkaAPIConfiguration = enkaAPIConfiguration;
        this.cacheManager = cacheManager;
        this.lastFetchTime = LocalDateTime.MIN;
    }

    /**
     * @return the specific store name this file cache belongs to
     */
    public abstract EnkaStoreType getStoreType();

    /**
     * @return the actual name of the file (e.g. 'locs.json')
     */
    public String getStoreName() {
        return this.getStoreType().getFileName();
    }

    /**
     * @return the name of the cache that we are using,
     * or null if we don't use spring caching
     */
    public abstract String getCacheName();

    /**
     * gets called when we grab a new file so we can react to changes,
     * by default clears the spring cache associated
     */
    protected void onFileStoreRefreshed() {
        log.info("Saved new enka store file: {}", this.getStoreName());
        if(!StringUtils.isBlank(this.getCacheName())) {
            Cache fileCache = cacheManager.getCache(this.getCacheName());
            if(fileCache != null) {
                fileCache.clear();
            }
        }
    }

    /**
     * parse the saved enka store file into a JSON node
     * and fetch it again if needed
     * @return a JsonNode of the root of the store file
     */
    protected JsonNode getRootNode() {
        File storeFile = new File(enkaAPIConfiguration.getConfigurationPath(), this.getStoreName());

        LocalDateTime currentTime = LocalDateTime.now();
        if(!storeFile.exists() || currentTime.isAfter(lastFetchTime.plusHours(enkaAPIConfiguration.getRefreshTimeInHours()))) {
            this.saveEnkaStoreToFile();
            ObjectMapper objectMapper = new ObjectMapper();
            rootNode = objectMapper.readTree(storeFile);
        }

        return rootNode;
    }

    /**
     * try to save the file from the url specified in the enka api configuration
     * to local disc
     */
    private void saveEnkaStoreToFile() {
        File configFolder = new File(enkaAPIConfiguration.getConfigurationPath());
        if(!configFolder.exists()) {
            boolean configFolderCreated = configFolder.mkdir();
            if(!configFolderCreated) {
                log.error("Failed to create configuration folder {}", configFolder.getPath());
            }
        }

        String fileName = this.getStoreName();

        String fileUrl = enkaAPIConfiguration.getStoresMap().get(fileName);
        if(StringUtils.isBlank(fileUrl)) {
            log.error("Did not find {} in enka api configuration", this.getStoreName());
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(new File(configFolder, fileName));
             ReadableByteChannel rbc = Channels.newChannel(new URI(fileUrl).toURL().openStream())) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            this.onFileStoreRefreshed();
        }
        catch(IOException | URISyntaxException e) {
            log.error("Failed to read and save enka store file {} from url {}",
                    fileName, fileUrl, e);
        }
        finally {
             // always update last fetch time even if we failed to grab the file
             // this prevents trying to fetch it every call
            this.lastFetchTime = LocalDateTime.now();
        }
    }
}
