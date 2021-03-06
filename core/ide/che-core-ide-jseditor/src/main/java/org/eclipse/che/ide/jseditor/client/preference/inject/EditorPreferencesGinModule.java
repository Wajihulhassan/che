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
package org.eclipse.che.ide.jseditor.client.preference.inject;


import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.gwt.inject.client.multibindings.GinMultibinder;

import org.eclipse.che.ide.api.extension.ExtensionGinModule;
import org.eclipse.che.ide.api.preferences.PreferencePagePresenter;
import org.eclipse.che.ide.jseditor.client.preference.EditorPreferencePresenter;
import org.eclipse.che.ide.jseditor.client.preference.EditorPreferenceSection;
import org.eclipse.che.ide.jseditor.client.preference.EditorPreferenceView;
import org.eclipse.che.ide.jseditor.client.preference.EditorPreferenceViewImpl;
import org.eclipse.che.ide.jseditor.client.preference.editorproperties.sections.EditorPreferenceSectionFactory;
import org.eclipse.che.ide.jseditor.client.preference.editorproperties.sections.EditorPropertiesSection;
import org.eclipse.che.ide.jseditor.client.preference.editorproperties.propertiessection.EditorPropertiesSectionPresenter;
import org.eclipse.che.ide.jseditor.client.preference.editorproperties.sections.LanguageToolsPropertiesSection;
import org.eclipse.che.ide.jseditor.client.preference.editorproperties.sections.RulersPropertiesSection;
import org.eclipse.che.ide.jseditor.client.preference.editorproperties.sections.TabsPropertiesSection;
import org.eclipse.che.ide.jseditor.client.preference.editorproperties.sections.TypingPropertiesSection;
import org.eclipse.che.ide.jseditor.client.preference.editorproperties.sections.WhiteSpacesPropertiesSection;
import org.eclipse.che.ide.jseditor.client.preference.keymaps.KeyMapsPreferencePresenter;
import org.eclipse.che.ide.jseditor.client.preference.keymaps.KeymapsPreferenceView;
import org.eclipse.che.ide.jseditor.client.preference.keymaps.KeymapsPreferenceViewImpl;

/** Gin module for the editor preferences. */
@ExtensionGinModule
public class EditorPreferencesGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        // Bind the editor preference panel
        final GinMultibinder<PreferencePagePresenter> prefBinder = GinMultibinder.newSetBinder(binder(), PreferencePagePresenter.class);
        prefBinder.addBinding().to(EditorPreferencePresenter.class);

        bind(EditorPreferenceView.class).to(EditorPreferenceViewImpl.class);
        bind(KeymapsPreferenceView.class).to(KeymapsPreferenceViewImpl.class);
        bind(KeyMapsPreferencePresenter.class);

        install(new GinFactoryModuleBuilder().implement(EditorPreferenceSection.class, EditorPropertiesSectionPresenter.class)
                                             .build(EditorPreferenceSectionFactory.class));

        final GinMultibinder<EditorPropertiesSection> editorPropertiesSectionBinder = GinMultibinder.newSetBinder(binder(), EditorPropertiesSection.class);
        editorPropertiesSectionBinder.addBinding().to(TabsPropertiesSection.class);
        editorPropertiesSectionBinder.addBinding().to(LanguageToolsPropertiesSection.class);
        editorPropertiesSectionBinder.addBinding().to(TypingPropertiesSection.class);
        editorPropertiesSectionBinder.addBinding().to(WhiteSpacesPropertiesSection.class);
        editorPropertiesSectionBinder.addBinding().to(RulersPropertiesSection.class);
    }
}
