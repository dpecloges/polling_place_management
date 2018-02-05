package com.ots.dpel.global.tpl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * A simple text renderer which uses <a
 * href="http://freemarker.org/">FreeMarker</a> template engine to generate
 * formatted text.
 * 
 * @author Kostas Tzonas <ktzonas@ots.gr>
 */
public class FreemarkerRenderer implements TextTemplateRenderer {
	private static final String NAME = "freemarker";
	private static final String TPL_NAME = "template";

	@Override
	public String render(TextTemplate template, Map<String, Object> args) {
		StringWriter writer = new StringWriter();

		try {
			Template tpl = new Template(TPL_NAME, new StringReader(
					template.getTemplate()), new Configuration(
					Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
			tpl.process(args, writer);
			String txt = writer.toString();
			writer.flush();
			return txt;
		} catch (IOException | TemplateException e) {
			throw new InvalidTemplateException(e);
		}
	}

	@Override
	public String name() {
		return NAME;
	}
}
