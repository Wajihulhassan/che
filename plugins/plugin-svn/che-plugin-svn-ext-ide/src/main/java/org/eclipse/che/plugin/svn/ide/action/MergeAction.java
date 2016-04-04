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
package org.eclipse.che.plugin.svn.ide.action;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.ide.api.action.ActionEvent;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.plugin.svn.ide.SubversionExtensionLocalizationConstants;
import org.eclipse.che.plugin.svn.ide.SubversionExtensionResources;
import org.eclipse.che.plugin.svn.ide.merge.MergePresenter;
import org.eclipse.che.ide.part.explorer.project.ProjectExplorerPresenter;

/**
 * Extension of {@link SubversionAction} for implementing the "svn merge" command.
 */
@Singleton
public class MergeAction extends SubversionAction {

    private final MergePresenter presenter;

    @Inject
    public MergeAction(final AppContext appContext,
                       final ProjectExplorerPresenter projectExplorerPresenter,
                       final SubversionExtensionLocalizationConstants constants,
                       final SubversionExtensionResources resources,
                       final MergePresenter presenter) {
        super(constants.mergeTitle(), constants.mergeDescription(), resources.merge(), appContext,
              constants, resources, projectExplorerPresenter);

        this.presenter = presenter;
    }

    @Override
    protected boolean isSelectionRequired() {
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        presenter.merge();
    }

}
