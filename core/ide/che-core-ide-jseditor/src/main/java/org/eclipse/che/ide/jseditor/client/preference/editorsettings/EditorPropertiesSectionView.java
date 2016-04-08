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
import com.google.inject.ImplementedBy;


import org.eclipse.che.ide.api.mvp.View;

import javax.validation.constraints.NotNull;

/**
 * Provides methods to control panel of propertiesPanel.
 *
 * @author Roman Nikitenko
 */
@ImplementedBy(EditorPropertiesSectionViewImpl.class)
public interface EditorPropertiesSectionView extends View<EditorPropertiesSectionView.ActionDelegate> {

    /**
     * Adds special property widget on special panel on view.
     *
     * @param propertyWidget
     *         widget which will be added
     */
    void addProperty(@NotNull EditorOptions property, JSONValue value);

    JSONValue getPropertyValueById(@NotNull EditorOptions propertyId);

    interface ActionDelegate {
        /**
         * Performs some action when user change value of property.
         */
        void onPropertyChanged();
    }
}
