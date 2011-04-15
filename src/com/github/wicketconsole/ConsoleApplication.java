package com.github.wicketconsole;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.util.lang.WicketObjects;

import com.github.wicketconsole.pages.AccessDeniedPage;
import com.github.wicketconsole.pages.ClojurePage;
import com.github.wicketconsole.pages.ClojureWithTemplatesPage;
import com.github.wicketconsole.pages.GroovyPage;
import com.github.wicketconsole.pages.GroovyWithTemplatesPage;
import com.github.wicketconsole.pages.InternalErrorPage;
import com.github.wicketconsole.pages.PageExpiredPage;

public class ConsoleApplication extends WebApplication {

    private ConsoleModificationWatcher watcher;

    @Override
    protected void init() {
        super.init();

        setPageManagerProvider(new HttpSessionPageManagerProvider(this));

        watcher = new ConsoleModificationWatcher();
        getResourceSettings().setResourceWatcher(watcher);

        WicketObjects.setObjectStreamFactory(new ConsoleObjectStreamFactory());

        getExceptionSettings().setUnexpectedExceptionDisplay(
                IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);
        getApplicationSettings().setInternalErrorPage(InternalErrorPage.class);
        getApplicationSettings().setPageExpiredErrorPage(PageExpiredPage.class);
        getApplicationSettings().setAccessDeniedPage(AccessDeniedPage.class);

        mountPage("groovy", GroovyPage.class);
        mountPage("groovyWithTemplates", GroovyWithTemplatesPage.class);
        mountPage("clojure", ClojurePage.class);
        mountPage("clojureWithTemplates", ClojureWithTemplatesPage.class);
    }

    @Override
    public Session newSession(final Request request, final Response response) {
        final WebSession webSession = new WebSession(request);
        webSession.bind();
        return webSession;
    }

    @Override
    protected WebRequest newWebRequest(final HttpServletRequest servletRequest,
            final String filterPath) {
        if (getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT) {
            watcher.check();
        }
        return super.newWebRequest(servletRequest, filterPath);
    }

    @Override
    public RuntimeConfigurationType getConfigurationType() {
        return RuntimeConfigurationType.DEPLOYMENT;
        // return RuntimeConfigurationType.DEVELOPMENT;
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return GroovyPage.class;
    }

}
