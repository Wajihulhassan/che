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

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import org.eclipse.che.ide.jseditor.client.preference.editorsettings.property.EditorPropertyNameManager;
import org.eclipse.che.ide.jseditor.client.preference.editorsettings.property.EditorPropertyWidget;
import org.eclipse.che.ide.jseditor.client.preference.editorsettings.property.EditorPropertyWidgetFactory;
import org.eclipse.che.ide.util.loging.Log;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * The class provides special panel to store special property widgets which allow setup compiler. Also the class contains methods
 * to control this panel.
 *
 * @author Roman Nikitenko
 */
public class EditorPropertiesSectionViewImpl extends Composite implements EditorPropertiesSectionView, EditorPropertyWidget.ActionDelegate {

    private static final CompilerSetupViewImplUiBinder UI_BINDER = GWT.create(CompilerSetupViewImplUiBinder.class);

    @UiField
    FlowPanel propertiesPanel;

    private final EditorPropertyWidgetFactory editorPropertyWidgetFactory;
    private final EditorPropertyNameManager   editorPropertyNameManager;
    private       ActionDelegate              delegate;
    private Map<EditorOptions, EditorPropertyWidget> properties = new HashMap<>();

    @Inject
    public EditorPropertiesSectionViewImpl(EditorPropertyWidgetFactory editorPropertyWidgetFactory,
                                           EditorPropertyNameManager editorPropertyNameManager) {
        this.editorPropertyWidgetFactory = editorPropertyWidgetFactory;
        this.editorPropertyNameManager = editorPropertyNameManager;

        initWidget(UI_BINDER.createAndBindUi(this));
    }

    @Override
    public void onPropertyChanged() {
        delegate.onPropertyChanged();
    }

    @Override
    public void setDelegate(ActionDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public JSONValue getPropertyValueById(@NotNull EditorOptions propertyId) {
        EditorPropertyWidget propertyWidget = properties.get(propertyId);
        if (propertyWidget != null) {
            return propertyWidget.getValue();
        }

        return null;
    }

    @Override
    public void addProperty(@NotNull EditorOptions property, JSONValue value) {
        EditorPropertyWidget propertyWidget;
        String propertyName = editorPropertyNameManager.getPropertyNameById(property);
        propertyWidget = value.isBoolean() != null ? editorPropertyWidgetFactory.create(propertyName, value.isBoolean().booleanValue())
                                                   : editorPropertyWidgetFactory.create(propertyName, value.toString());
        propertyWidget.setDelegate(this);

        propertiesPanel.add(propertyWidget);
        properties.put(property, propertyWidget);
    }

    interface CompilerSetupViewImplUiBinder extends UiBinder<Widget, EditorPropertiesSectionViewImpl> {
    }
}
