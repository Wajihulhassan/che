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
package org.eclipse.che.ide.jseditor.client;

import com.google.gwt.i18n.client.Messages;

/**
 * I18n Constants for the JsEditor module.
 *
 * @author "Mickaël Leduque"
 */
public interface JsEditorConstants extends Messages {

    @DefaultMessage("Default Editor")
    String defaultEditorDescription();

    @DefaultMessage("Unidentified File")
    String infoPanelUnknownFileType();

    // space is meaningful
    @DefaultMessage("Line ")
    String infoPaneLineLabel();

    // spaces and comma are meaningful
    @DefaultMessage(", Char ")
    String infoPanelCharacterLabel();

    // space is meaningful
    @DefaultMessage("Tab Size: ")
    String infoPaneTabSizeLabel();

    @DefaultMessage("Editor: ")
    String infoPaneEditorLabel();

    @DefaultMessage("Key Bindings: ")
    String infoPaneKeybindingLabel();

    @DefaultMessage("Unknown")
    String infoPanelUnknownEditorType();

    @DefaultMessage("Unknown")
    String infoPanelUnknownKeybindings();

    @DefaultMessage("Close")
    String askWindowCloseTitle();

    @DefaultMessage("{0} has been modified. Save changes?")
    String askWindowSaveChangesMessage(String name);

    @DefaultMessage("An error occurred while initializing the editor.\nReloading the page may be necessary.")
    String editorInitErrorMessage();

    @DefaultMessage("An error occurred while loading the file.")
    String editorFileErrorMessage();

    @DefaultMessage("Finishing editor initialization")
    String waitEditorInitMessage();

    @DefaultMessage("File changed")
    String fileUpdateTitle();

    @DefaultMessage("The content for the file named {0} has been changed on the server.<br>"
                    + " Do you wish to keep your unsaved changes or do you want to overwrite them with changes from the server?")
    String fileUpdateMessage(String path);

    @DefaultMessage("Overwrite")
    String fileUpdateOverwrite();

    @DefaultMessage("Keep my changes")
    String fileUpdateKeepUnsaved();

    @DefaultMessage("Failed to update content of file(s)")
    String failedToUpdateContentOfFiles();

    @DefaultMessage("Tabs")
    String tabsPropertiesSection();

    @DefaultMessage("Typing")
    String typingPropertiesSection();

    @DefaultMessage("White spaces")
    String whiteSpacesPropertiesSection();

    @DefaultMessage("Rulers")
    String rulersPropertiesSection();

    @DefaultMessage("Language tools")
    String languageToolsPropertiesSection();

    @DefaultMessage("Expand Tab:")
    String propertyExpandTab();

    @DefaultMessage("Tab Size:")
    String propertyTabSize();

    @DefaultMessage("Autopair (Parentheses):")
    String propertyAutoPairParentheses();

    @DefaultMessage("Autopair Braces:")
    String propertyAutoPairBraces();

    @DefaultMessage("Autopair [Square] Brackets:")
    String propertyAutoPairSquareBrackets();

    @DefaultMessage("Autopair <Angle> Brackets:")
    String propertyAutoPairAngelBrackets();

    @DefaultMessage("Autopair \"Quotations\":")
    String propertyAutoPairQuotations();

    @DefaultMessage("Autocomplete /** Block Comments */:")
    String propertyAutoCompleteComments();

    @DefaultMessage("Smart Indentation:")
    String propertySmartIndentation();

    @DefaultMessage("Show Whitespace Characters:")
    String propertyShowWhitespaces();

    @DefaultMessage("Show Annotation Ruler:")
    String propertyShowAnnotationRuler();

    @DefaultMessage("Show Line Number Ruler:")
    String propertyShowLineNumberRuler();

    @DefaultMessage("Show Folding Ruler:")
    String propertyShowFoldingRuler();

    @DefaultMessage("Show Overview Ruler:")
    String propertyShowOverviewRuler();

    @DefaultMessage("Show Zoom Ruler:")
    String propertyShowZoomRuler();

    @DefaultMessage("Show Occurrences:")
    String propertyShowOccurrences();

    @DefaultMessage("Show Content Assist automatically:")
    String propertyShowContentAssistAutomatically();
}
