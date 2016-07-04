package core.json.model;

import com.google.gson.annotations.SerializedName;

public class AppSettingsModel {

    private boolean debug;
    @SerializedName("translator_cache_size")
    private int translatorCacheSize;
    @SerializedName("node_cache_size")
    private int nodeCacheSize;

    public AppSettingsModel() {
    }

    public void setDebug(boolean debug) {
	this.debug = debug;
    }

    public boolean getDebug() {
	return this.debug;
    }
    
    public int getTranslatorCahceSize() {
	return this.translatorCacheSize;
    }
    
    public int getNodeCacheSize() {
	return this.nodeCacheSize;
    }

}
