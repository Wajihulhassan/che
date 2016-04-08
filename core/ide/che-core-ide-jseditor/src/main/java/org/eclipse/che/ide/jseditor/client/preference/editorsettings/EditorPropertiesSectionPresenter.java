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
package org.eclipse.che.ide.jseditor.client.preference.editorsettings;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import org.eclipse.che.api.machine.gwt.client.events.WsAgentStateEvent;
import org.eclipse.che.api.machine.gwt.client.events.WsAgentStateHandler;
import org.eclipse.che.ide.jseditor.client.editortype.EditorType;
import org.eclipse.che.ide.jseditor.client.editortype.EditorTypeRegistry;
import org.eclipse.che.ide.jseditor.client.keymap.Keymap;
import org.eclipse.che.ide.jseditor.client.preference.EditorPreferenceSection;
import org.eclipse.che.ide.jseditor.client.preference.editorsettings.property.EditorPropertyNameManager;
import org.eclipse.che.ide.util.loging.Log;

import java.util.Map;

/** Presenter for the editor propertiesPanel section in the 'Preferences' menu. */
public class EditorPropertiesSectionPresenter implements EditorPreferenceSection, EditorPropertiesSectionView.ActionDelegate,
                                                         WsAgentStateHandler {

    private final EditorPropertiesSectionView view;

    private final EventBus         eventBus;

    /** Has any of the keymap preferences been changed ? */
    private boolean dirty = false;

    /** The preference page presenter. */
    private ParentPresenter parentPresenter;

    private final EditorTypeRegistry editorTypeRegistry;

    private final EditorPropertyNameManager editorPropertyNameManager;

    @Inject
    public EditorPropertiesSectionPresenter(final EditorPropertiesSectionView view,
                                            final EventBus eventBus,
                                            final EditorTypeRegistry editorTypeRegistry,
                                            final EditorPropertyNameManager editorPropertyNameManager) {
        this.view = view;
        this.view.setDelegate(this);
        this.eventBus = eventBus;
        this.editorTypeRegistry = editorTypeRegistry;
        this.editorPropertyNameManager = editorPropertyNameManager;

        eventBus.addHandler(WsAgentStateEvent.TYPE, this);

//        this.view.setDelegate(this);
    }

    @Override
    public void storeChanges() {
        Log.error(getClass(), "=== storeChanges === ");
//        keymapPrefReader.storePrefs(this.keymapValuesHolder);
//        for (final Entry<EditorType, Keymap> entry : this.keymapValuesHolder) {
//            this.eventBus.fireEvent(new KeymapChangeEvent(entry.getKey().getEditorTypeKey(), entry.getValue().getKey()));
//        }
//        dirty = false;
    }

    @Override
    public void refresh() {
//        readPreferenceFromPreferenceManager();
//        view.refresh();
    }

    @Override
    public boolean isDirty() {
        Map<EditorOptions, JSONValue> editorProperties = EditorPropertyNameManager.getEditorProperties();
        for (EditorOptions property : editorProperties.keySet()) {
            if (!editorProperties.get(property).equals(view.getPropertyValueById(property))) {
                Log.error(getClass(), "dirty  " + property.toString());
                return true;
            }
        }
        return false;
    }

    @Override
    public void go(final AcceptsOneWidget container) {
        container.setWidget(view);
    }

    @Override
    public void setParent(final ParentPresenter parent) {
        this.parentPresenter = parent;
    }

    @Override
    public void onWsAgentStarted(WsAgentStateEvent event) {
        Map<EditorOptions, JSONValue> editorProperties = EditorPropertyNameManager.getEditorProperties();
        for (EditorOptions property : editorProperties.keySet()) {
            JSONValue value = editorProperties.get(property);
            view.addProperty(property, value);
        }
    }

    @Override
    public void onWsAgentStopped(WsAgentStateEvent event) {

    }

    @Override
    public void onPropertyChanged() {
        parentPresenter.signalDirtyState();
    }
}
