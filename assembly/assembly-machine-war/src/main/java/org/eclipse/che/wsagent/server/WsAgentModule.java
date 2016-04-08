/*******************************************************************************
 * Copyright (c) 2012-2016 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.wsagent.server;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

import org.eclipse.che.api.auth.oauth.OAuthTokenProvider;
import org.eclipse.che.api.core.notification.WSocketEventBusClient;
import org.eclipse.che.api.core.rest.ApiInfoService;
import org.eclipse.che.api.core.rest.CoreRestModule;
import org.eclipse.che.api.git.GitConnectionFactory;
import org.eclipse.che.api.git.GitUserResolver;
import org.eclipse.che.api.project.server.ProjectApiModule;
import org.eclipse.che.api.ssh.server.HttpSshServiceClient;
import org.eclipse.che.api.ssh.server.SshServiceClient;
import org.eclipse.che.api.user.server.dao.PreferenceDao;
import org.eclipse.che.api.vfs.VirtualFileSystemModule;
import org.eclipse.che.commons.lang.Pair;
import org.eclipse.che.everrest.CheAsynchronousJobPool;
import org.eclipse.che.generator.archetype.ArchetypeGenerator;
import org.eclipse.che.generator.archetype.ArchetypeGeneratorModule;
import org.eclipse.che.git.impl.jgit.JGitConnectionFactory;
import org.eclipse.che.git.impl.nativegit.LocalGitUserResolver;
import org.eclipse.che.ide.ext.java.jdi.server.DebuggerService;
import org.eclipse.che.ide.extension.maven.server.inject.MavenModule;
import org.eclipse.che.inject.DynaModule;
import org.eclipse.che.plugin.github.server.inject.GitHubModule;
import org.eclipse.che.security.oauth.RemoteOAuthTokenProvider;
import org.everrest.core.impl.async.AsynchronousJobPool;
import org.everrest.core.impl.async.AsynchronousJobService;
import org.everrest.guice.ServiceBindingHelper;

import javax.inject.Named;
import java.net.URI;

/**
 * @author Evgen Vidolob
 */
@DynaModule
public class WsAgentModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ApiInfoService.class);

        bind(PreferenceDao.class).to(org.eclipse.che.api.local.RemotePreferenceDao.class);

        bind(OAuthTokenProvider.class).to(RemoteOAuthTokenProvider.class);
        bind(SshServiceClient.class).to(HttpSshServiceClient.class);

        bind(org.eclipse.che.git.impl.nativegit.ssh.SshKeyProvider.class)
                .to(org.eclipse.che.git.impl.nativegit.ssh.SshKeyProviderImpl.class);

        install(new CoreRestModule());
        install(new VirtualFileSystemModule());
        install(new ProjectApiModule());
        install(new MavenModule());
        install(new ArchetypeGeneratorModule());
        install(new GitHubModule());
        install(new org.eclipse.che.swagger.deploy.DocsModule());

        bind(ArchetypeGenerator.class);
        bind(DebuggerService.class);

        bind(GitUserResolver.class).to(LocalGitUserResolver.class);
        bind(GitConnectionFactory.class).to(JGitConnectionFactory.class);

        bind(AsynchronousJobPool.class).to(CheAsynchronousJobPool.class);
        bind(ServiceBindingHelper.bindingKey(AsynchronousJobService.class, "/async/{ws-id}")).to(AsynchronousJobService.class);

        bind(String.class).annotatedWith(Names.named("api.endpoint")).toProvider(ApiEndpointProvider.class);
        bind(URI.class).annotatedWith(Names.named("api.endpoint")).toProvider(UriApiEndpointProvider.class);
        bind(String.class).annotatedWith(Names.named("user.token")).toProvider(UserTokenProvider.class);
        bind(WSocketEventBusClient.class).asEagerSingleton();

        bind(String.class).annotatedWith(Names.named("event.bus.url")).toProvider(EventBusURLProvider.class);
        bind(ApiEndpointAccessibilityChecker.class);
    }

    //it's need for WSocketEventBusClient and in the future will be replaced with the property
    @Named("notification.client.event_subscriptions")
    @Provides
    @SuppressWarnings("unchecked")
    Pair<String, String>[] eventSubscriptionsProvider(@Named("event.bus.url") String eventBusURL) {
        return new Pair[] {Pair.of(eventBusURL, "")};
    }

    //it's need for EventOriginClientPropagationPolicy and in the future will be replaced with the property
    @Named("notification.client.propagate_events")
    @Provides
    @SuppressWarnings("unchecked")
    Pair<String, String>[] propagateEventsProvider(@Named("event.bus.url") String eventBusURL) {
        return new Pair[] {Pair.of(eventBusURL, "")};
    }
}
