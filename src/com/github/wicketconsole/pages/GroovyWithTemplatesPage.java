package com.github.wicketconsole.pages;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.wicketstuff.console.GroovyScriptEngineWithTemplatesPanel;
import org.wicketstuff.console.engine.Lang;
import org.wicketstuff.console.templates.PackagedScriptTemplates;
import org.wicketstuff.console.templates.ScriptTemplate;

public class GroovyWithTemplatesPage extends ConsoleBasePage {

    private static final long serialVersionUID = 1L;
    private final GroovyScriptEngineWithTemplatesPanel panel;

    public GroovyWithTemplatesPage() {

        final IDataProvider<ScriptTemplate> dp = new ListDataProvider<ScriptTemplate>(
                PackagedScriptTemplates.getPackagedScriptTemplates(Lang.GROOVY));
        panel = new GroovyScriptEngineWithTemplatesPanel("console", null, dp);

        panel.getEnginePanel()
                .setInput(
                        "import org.apache.wicket.ajax.AjaxRequestTarget;\n"
                                + "\n"
                                + "def templateId = 1\n"
                                + "def host = \"http://scriptdonkey.appspot.com\"\n"
                                + "def url = new java.net.URL(\"${host}/api/templates/${templateId}\")\n"
                                + "def con = url.openConnection()\n"
                                + "con.setConnectTimeout(10000)\n"
                                + "\n"
                                + "def xml = new XmlSlurper().parse(con.getContent())\n"
                                + "component.setInput(xml.script.text())\n"
                                + "AjaxRequestTarget.get().add(component.getInputTf())\n");

        add(panel);

    }

}
