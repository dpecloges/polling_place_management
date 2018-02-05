package com.ots.dpel.global.tpl;

/**
 * A named text template. The template may contain placeholders for dynamic
 * content. These placeholders are to be interpreted according to the
 * {@link TextTemplateRenderer} that is used to process the template.
 *
 * @author Kostas Tzonas <ktzonas@ots.gr>
 */
public class TextTemplate {
	private final String template;
	private final String name;

	public TextTemplate(String template) {
		this(template, null);
	}

	public TextTemplate(String name, String template) {

		this.template = template;
		this.name = name;
	}

	public String getTemplate() {
		return template;
	}

	public String getName() {
		return name;
	}
}
