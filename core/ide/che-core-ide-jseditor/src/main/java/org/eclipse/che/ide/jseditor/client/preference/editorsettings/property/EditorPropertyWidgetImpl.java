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
package org.eclipse.che.ide.jseditor.client.preference.editorsettings.property;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import org.eclipse.che.ide.jseditor.client.preference.editorsettings.EditorOptions;
import org.eclipse.che.ide.util.loging.Log;

import javax.validation.constraints.NotNull;

/**
 * @author Roman Nikitenko
 */
public class EditorPropertyWidgetImpl extends Composite implements EditorPropertyWidget {
    interface PropertyWidgetImplUiBinder extends UiBinder<Widget, EditorPropertyWidgetImpl> {
    }

    private static final PropertyWidgetImplUiBinder UI_BINDER = GWT.create(PropertyWidgetImplUiBinder.class);

    private EditorOptions optionId;

    String propertyName;

    @UiField
    Label     title;
    @UiField
    FlowPanel valuePanel;

    HasValue valueHolder;

    private ActionDelegate delegate;

    private final boolean isBooleanProperty;

    @AssistedInject
    public EditorPropertyWidgetImpl(final EditorPropertyNameManager nameManager,
                                    @Assisted("propertyName") String propertyName,
                                    @Assisted("value") boolean value) {
        initWidget(UI_BINDER.createAndBindUi(this));

        isBooleanProperty = true;

        this.propertyName = propertyName;

        this.title.setText(propertyName);
        CheckBox propertyValueBox = new CheckBox();
        propertyValueBox.setValue(value);
        propertyValueBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                delegate.onPropertyChanged();
            }
        });
        valuePanel.add(propertyValueBox);
        valueHolder = propertyValueBox;
    }

    @AssistedInject
    public EditorPropertyWidgetImpl(final EditorPropertyNameManager nameManager,
                                    @Assisted("propertyName") String propertyName,
                                    @Assisted("value") String value) {
        initWidget(UI_BINDER.createAndBindUi(this));
        isBooleanProperty = false;

        this.propertyName = propertyName;

        this.title.setText(propertyName);
        TextBox propertyValueBox = new TextBox();
        propertyValueBox.setVisibleLength(5);
        propertyValueBox.setValue(value);
        propertyValueBox.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                delegate.onPropertyChanged();
            }
        });
        valuePanel.add(propertyValueBox);
        valueHolder = propertyValueBox;
    }

    /** {@inheritDoc} */
    @Override
    public void selectPropertyValue(@NotNull String value) {
//        for (int i = 0; i < property.getItemCount(); i++) {
//            if (property.getValue(i).equals(value)) {
//                property.setItemSelected(i, true);
//                return;
//            }
//        }
    }

//    @UiHandler("property")
//    public void onPropertyChanged(@SuppressWarnings("UnusedParameters") ChangeEvent event) {
//        delegate.onPropertyChanged();
//    }

    @Override
    public JSONValue getValue() {
//        Log.error(getClass(), "8888 value " + JSONParser.parseStrict(valueHolder.getValue().toString()).isBoolean());
        if (isBooleanProperty) {
            return JSONBoolean.getInstance((boolean)valueHolder.getValue());
        }
        return new JSONNumber(new Double(valueHolder.getValue().toString()));
    }

    @Override
    public EditorOptions getOptionId() {
        return optionId;
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(@NotNull ActionDelegate delegate) {
        this.delegate = delegate;
    }
}
