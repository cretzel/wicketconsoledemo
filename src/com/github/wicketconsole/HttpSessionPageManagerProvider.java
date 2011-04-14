package com.github.wicketconsole;

import org.apache.wicket.Application;
import org.apache.wicket.DefaultPageManagerProvider;
import org.apache.wicket.page.IPageManager;
import org.apache.wicket.page.IPageManagerContext;
import org.apache.wicket.page.PersistentPageManager;
import org.apache.wicket.pageStore.DefaultPageStore;
import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.pageStore.IPageStore;
import org.apache.wicket.pageStore.memory.HttpSessionDataStore;
import org.apache.wicket.pageStore.memory.PageNumberEvictionStrategy;

final class HttpSessionPageManagerProvider extends
        DefaultPageManagerProvider {
    private final Application application;

    HttpSessionPageManagerProvider(final Application application) {
        super(application);
        this.application = application;
    }

    @Override
    public IPageManager get(final IPageManagerContext pageManagerContext) {
        final IDataStore dataStore = new HttpSessionDataStore(
                pageManagerContext, new PageNumberEvictionStrategy(100));
        final IPageStore pageStore = new DefaultPageStore(application.getName(),
                dataStore, getCacheSize());
        return new PersistentPageManager(application.getName(), pageStore,
                pageManagerContext);
    }
}