package com.ots.dpel.global.tpl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * A registry of {@link TextTemplateRenderer}s. It is initialized with a list of
 * {@link TextTemplateRenderer}s, which are then registered by their distinctive
 * name, and can later be retrieved with it.
 *
 * @author Kostas Tzonas <ktzonas@ots.gr>
 */
public class TextTemplateRendererProvider {
	private final Map<String, TextTemplateRenderer> renderersMap = new HashMap<>();

	public TextTemplateRendererProvider(
			final List<TextTemplateRenderer> renderers) {
		for (TextTemplateRenderer renderer : renderers) {
			String name = renderer.name();
			if (renderersMap.containsKey(name)) {
				throw new IllegalArgumentException("Duplicate renderer name "
						+ name);
			}
			renderersMap.put(name, renderer);
		}
	}

	public TextTemplateRenderer getTextRenderer(String name) {
		return renderersMap.get(name);
	}

	public boolean isTextRenderer(String name) {
		return renderersMap.containsKey(name);
	}
}
