package com.ots.dpel.global.tpl;

import java.util.Map;

/**
 *
 * @author Kostas Tzonas <ktzonas@ots.gr>
 */
public class SimpleRenderer implements TextTemplateRenderer {
	private static final String NAME = "simple";
	private static final String DEFAULT_BOUNDARY = "%";

	private String prefix;
	private String suffix;

	public SimpleRenderer() {
		this(DEFAULT_BOUNDARY);
	}

	public SimpleRenderer(String boundary) {
		this.prefix = boundary;
		this.suffix = boundary;
	}

	public SimpleRenderer(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}

	@Override
	public String render(TextTemplate template, Map<String, Object> args) {
		return formatWithArgs(template.getTemplate(), args);
	}

	private String formatWithArgs(String template,
			Map<String, Object> templateArgs) {

		for (String arg : templateArgs.keySet()) {
			if (templateArgs.get(arg) == null) {
				continue;
			}

			template = template.replaceAll(prefix + arg + suffix, templateArgs
					.get(arg).toString());
		}
		return template;
	}

	@Override
	public String name() {
		return NAME;
	}
}
