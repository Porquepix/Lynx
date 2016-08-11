package com.lynx.core.translation;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.lynx.core.namespace.Namespace;
import com.lynx.core.namespace.Resource;

public class CacheFileTranslator implements Translator {

	private static final String TRANSLATION_ID_START = "t$";

	private Namespace root;
	private String lang;
	private LoadingCache<Namespace, Translator> cache;

	public CacheFileTranslator(Namespace root, String lang, int cachesize) {
		this.root = root.append(lang);
		this.lang = lang;
		this.cache = CacheBuilder.newBuilder()
				.maximumSize(cachesize)
				.expireAfterAccess(10, TimeUnit.MINUTES)
				.build(
						new CacheLoader<Namespace, Translator>() {
							public Translator load(Namespace key) {
								return createTranslator(key);
							}
						}
				);
	}

	public String getLang() {
		return this.lang;
	}

	public Namespace getRoot() {
		return this.root;
	}

	@Override
	public String translate(String string) {
		String escaped = Strings.nullToEmpty(string);
		if ( escaped.startsWith(TRANSLATION_ID_START) ) {
			return translateWithTranslator(escaped);
		} else {
			return escaped;
		}
	}

	@Override
	public List<String> translateAll(List<String> strings) {
		return strings.stream()
		        .map((String s) -> translate(s))
		        .collect(Collectors.toList());
	}

	private String translateWithTranslator(String string) {
		String translationId = string.substring(TRANSLATION_ID_START.length());
		Resource res = createResource(translationId);
		Translator translator = getTranslator(res.getNamespace());
		return translator.translate(res.getId());
	}
	
	public Translator getTranslator(Namespace name) {
	    return cache.getUnchecked(name);
    }
	
	private Translator createTranslator(Namespace relative) {
		Namespace absolute = root.merge(relative);
		return new FileTranslator(absolute);
	}
	
	private Resource createResource(String translationId) {
		return new Resource(translationId);
	}

}
