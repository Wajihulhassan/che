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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.ide.api.preferences.PreferencesManager;
import org.eclipse.che.ide.jseditor.client.JsEditorConstants;
import org.eclipse.che.ide.jseditor.client.preference.editorsettings.EditorOptions;
import org.eclipse.che.ide.json.JsonHelper;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.eclipse.che.ide.jseditor.client.preference.editorsettings.EditorOptions.AUTO_COMPLETE_COMMENTS;
import static org.eclipse.che.ide.jseditor.client.preference.editorsettings.EditorOptions.AUTO_PAIR_ANGLE_BRACKETS;
import static org.eclipse.che.ide.jseditor.client.preference.editorsettings.EditorOptions.AUTO_PAIR_BRACES;
import static org.eclipse.che.ide.jseditor.client.preference.editorsettings.EditorOptions.AUTO_PAIR_PARENTHESES;
import static org.eclipse.che.ide.jseditor.client.preference.editorsettings.EditorOptions.AUTO_PAIR_QUOTATIONS;
import static org.eclipse.che.ide.jseditor.client.preference.editorsettings.EditorOptions.AUTO_PAIR_SQUARE_BRACKETS;
import static org.eclipse.che.ide.jseditor.client.preference.editorsettings.EditorOptions.EXPAND_TAB;
import static org.eclipse.che.ide.jseditor.client.preference.editorsettings.EditorOptions.SHOW_ANNOTATION_RULER;
import static org.eclipse.che.ide.jseditor.client.preference.editorsettings.EditorOptions.SHOW_CONTENT_ASSIST_AUTOMATICALLY;
import static org.eclipse.che.ide.jseditor.client.preference.editorsettings.EditorOptions.SHOW_FOLDING_RULER;
import static org.eclipse.che.ide.jseditor.client.preference.editorsettings.EditorOptions.SHOW_LINE_NUMBER_RULER;
import static org.eclipse.che.ide.jseditor.client.preference.editorsettings.EditorOptions.SHOW_OCCURRENCES;
import static org.eclipse.che.ide.jseditor.client.preference.editorsettings.EditorOptions.SHOW_OVERVIEW_RULER;
import static org.eclipse.che.ide.jseditor.client.preference.editorsettings.EditorOptions.SHOW_ZOOM_RULER;
import static org.eclipse.che.ide.jseditor.client.preference.editorsettings.EditorOptions.SMART_INDENTATION;
import static org.eclipse.che.ide.jseditor.client.preference.editorsettings.EditorOptions.TAB_SIZE;
/**
 * The class contains editor properties IDs which match to properties' names.
 *
 * @author Roman Nikitenko
 */

@Singleton
public class EditorPropertyNameManager {

    private final  Map<EditorOptions, String>    names;
    private static Map<EditorOptions, JSONValue> defaultProperties;

    private PreferencesManager preferencesManager;

    @Inject
    public EditorPropertyNameManager(JsEditorConstants locale,
                                     PreferencesManager preferencesManager) {

        this.preferencesManager = preferencesManager;

        names = new HashMap<>();

        names.put(EXPAND_TAB, locale.propertyExpandTab());
        names.put(TAB_SIZE, locale.propertyTabSize());
        names.put(AUTO_PAIR_PARENTHESES, locale.propertyAutoPairParentheses());
        names.put(AUTO_PAIR_BRACES, locale.propertyAutoPairBraces());
        names.put(AUTO_PAIR_SQUARE_BRACKETS, locale.propertyAutoPairSquareBrackets());
        names.put(AUTO_PAIR_ANGLE_BRACKETS, locale.propertyAutoPairAngelBrackets());
        names.put(AUTO_PAIR_QUOTATIONS, locale.propertyAutoPairQuotations());
        names.put(AUTO_COMPLETE_COMMENTS, locale.propertyAutoCompleteComments());
        names.put(SMART_INDENTATION, locale.propertySmartIndentation());
        names.put(SHOW_ANNOTATION_RULER, locale.propertyShowAnnotationRuler());
        names.put(SHOW_LINE_NUMBER_RULER, locale.propertyShowLineNumberRuler());
        names.put(SHOW_FOLDING_RULER, locale.propertyShowFoldingRuler());
        names.put(SHOW_OVERVIEW_RULER, locale.propertyShowOverviewRuler());
        names.put(SHOW_ZOOM_RULER, locale.propertyShowZoomRuler());
        names.put(SHOW_OCCURRENCES, locale.propertyShowOccurrences());
        names.put(SHOW_CONTENT_ASSIST_AUTOMATICALLY, locale.propertyShowContentAssistAutomatically());
    }

    /**
     * Returns property name using special id. Method can throw {@link IllegalArgumentException} if name not found.
     *
     * @param propertyId
     *         id for which name will be returned
     * @return name of property
     */
    @NotNull
    public String getPropertyNameById(@NotNull EditorOptions propertyId) {
        String name = names.get(propertyId);

        if (name == null) {
            throw new IllegalArgumentException(getClass() + "property name is not found...");
        }

        return name;
    }

    public Set<EditorOptions> getKeys() {
        return names.keySet();
    }

    public Collection<String> getNames() {
        return names.values();
    }

    public static Map<EditorOptions, JSONValue> getEditorProperties() {
        return getDefaultEditorProperties();
    }

    public static Map<EditorOptions, JSONValue> getDefaultEditorProperties() {
        if (defaultProperties != null) {
            return defaultProperties;
        }
        defaultProperties = new HashMap<>();

        // TextViewOptions (tabs)
        defaultProperties.put(EXPAND_TAB, JSONBoolean.getInstance(true));
        defaultProperties.put(TAB_SIZE, new JSONNumber(4));

        // SourceCodeActions (typing)
        defaultProperties.put(AUTO_PAIR_PARENTHESES, JSONBoolean.getInstance(true));
        defaultProperties.put(AUTO_PAIR_BRACES, JSONBoolean.getInstance(true));
        defaultProperties.put(AUTO_PAIR_SQUARE_BRACKETS, JSONBoolean.getInstance(true));
        defaultProperties.put(AUTO_PAIR_ANGLE_BRACKETS, JSONBoolean.getInstance(true));
        defaultProperties.put(AUTO_PAIR_QUOTATIONS, JSONBoolean.getInstance(true));
        defaultProperties.put(AUTO_COMPLETE_COMMENTS, JSONBoolean.getInstance(true));
        defaultProperties.put(SMART_INDENTATION, JSONBoolean.getInstance(true));

        // editor features (rulers)
        defaultProperties.put(SHOW_ANNOTATION_RULER, JSONBoolean.getInstance(true));
        defaultProperties.put(SHOW_LINE_NUMBER_RULER, JSONBoolean.getInstance(true));
        defaultProperties.put(SHOW_FOLDING_RULER, JSONBoolean.getInstance(true));
        defaultProperties.put(SHOW_OVERVIEW_RULER, JSONBoolean.getInstance(true));
        defaultProperties.put(SHOW_ZOOM_RULER, JSONBoolean.getInstance(true));

        // language tools
        defaultProperties.put(SHOW_OCCURRENCES, JSONBoolean.getInstance(true));
        defaultProperties.put(SHOW_CONTENT_ASSIST_AUTOMATICALLY, JSONBoolean.getInstance(true));

        return defaultProperties;
    }

    public void storePreferences(JSONValue value) {
        preferencesManager.setValue("editorSettings", value.toString());
    }
}
