package com.github.wicketconsole.pages;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.wicketstuff.console.ClojureScriptEngineWithTemplatesPanel;
import org.wicketstuff.console.engine.Lang;
import org.wicketstuff.console.templates.PackagedScriptTemplates;
import org.wicketstuff.console.templates.ScriptTemplate;

public class ClojureWithTemplatesPage extends ConsoleBasePage {

    private static final long serialVersionUID = 1L;

    public ClojureWithTemplatesPage() {

        final IDataProvider<ScriptTemplate> dp = new ListDataProvider<ScriptTemplate>(
                PackagedScriptTemplates
                        .getPackagedScriptTemplates(Lang.CLOJURE));
        final ClojureScriptEngineWithTemplatesPanel enginePanel = new ClojureScriptEngineWithTemplatesPanel(
                "console", null, dp);
        add(enginePanel);

    }

}
