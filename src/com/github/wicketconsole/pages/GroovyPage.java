package com.github.wicketconsole.pages;

import org.wicketstuff.console.GroovyScriptEnginePanel;

public class GroovyPage extends ConsoleBasePage {

    private static final long serialVersionUID = 1L;

    public GroovyPage() {

        final GroovyScriptEnginePanel enginePanel = new GroovyScriptEnginePanel(
                "console");
        enginePanel
                .setInput("import org.apache.wicket.ajax.AjaxRequestTarget;\n"
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

        add(enginePanel);

    }

}
