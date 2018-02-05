package com.ots.dpel.global.tpl;

import java.util.Map;

/**
 * A simple template engine that creates strings based on a template and a
 * map-based model. Each renderer must has a distinctive name.
 *
 * @author Kostas Tzonas <ktzonas@ots.gr>
 */
public interface TextTemplateRenderer {
	String render(TextTemplate template, Map<String, Object> args);

	String name();
}

