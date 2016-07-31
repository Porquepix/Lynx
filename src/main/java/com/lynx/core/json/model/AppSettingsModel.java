package com.lynx.core.json.model;

import com.google.gson.annotations.SerializedName;
import com.lynx.core.json.JsonModel;

public class AppSettingsModel extends JsonModel {

    private boolean debug = false;
    @SerializedName("translator_cache_size")
    private int translatorCacheSize = 10;
    @SerializedName("node_cache_size")
    private int nodeCacheSize = 10;

    public AppSettingsModel() {
    }

    public void setDebug(boolean debug) {
	this.debug = debug;
    }

    public boolean getDebug() {
	return this.debug;
    }

    public int getTranslatorCacheSize() {
	return this.translatorCacheSize;
    }

    public int getNodeCacheSize() {
	return this.nodeCacheSize;
    }

}
