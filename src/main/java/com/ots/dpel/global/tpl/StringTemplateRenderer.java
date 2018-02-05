package com.ots.dpel.global.tpl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.ST;

/**
 * A simple text renderer which uses <a
 * href="http://www.stringtemplate.org/">StringTemplate</a> template engine to
 * generate formatted text.
 * 
 * @author Kostas Tzonas <ktzonas@ots.gr>
 */
public class StringTemplateRenderer implements TextTemplateRenderer {
	private static final String NAME = "stringTemplate";

	@Override
	public String render(TextTemplate template, Map<String, Object> args) {

		if (template == null || StringUtils.isBlank(template.getTemplate())) {
			throw new InvalidTemplateException();
		}

		ST st = new ST(template.getTemplate());
		for (String arg : args.keySet()) {
			st.add(arg, args.get(arg));
		}

		return st.toString();
	}

	@Override
	public String name() {
		return NAME;
	}
}
