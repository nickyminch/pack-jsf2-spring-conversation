## Copyright 2015 JAXIO http://www.jaxio.com
##
## Licensed under the Apache License, Version 2.0 (the "License");
## you may not use this file except in compliance with the License.
## You may obtain a copy of the License at
##
##    http://www.apache.org/licenses/LICENSE-2.0
##
## Unless required by applicable law or agreed to in writing, software
## distributed under the License is distributed on an "AS IS" BASIS,
## WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
## See the License for the specific language governing permissions and
## limitations under the License.
##
$output.java($WebModelSupport, "GenericToManyAssociation")##

$output.requireStatic($WebConversation, "ConversationHolder.getCurrentConversation")##
$output.require("java.io.Serializable")##
$output.require("java.util.List")##
$output.require("org.omnifaces.util.Faces")##
$output.require("org.primefaces.event.SelectEvent")##
$output.require("com.jaxio.jpa.querybyexample.Identifiable")##
$output.require("com.jaxio.jpa.querybyexample.GenericRepository")##
$output.require($WebConversation, "ConversationCallBack")##
$output.require($WebPermissionSupport, "GenericPermission")##
$output.require($WebUtil, "MessageUtil")##
$output.requireStatic("com.google.common.base.CaseFormat.LOWER_CAMEL")##
$output.requireStatic("com.google.common.base.CaseFormat.UPPER_CAMEL")##
$output.require("java.util.Collection")##
$output.require("javax.persistence.CascadeType")##
$output.require("javax.persistence.metamodel.PluralAttribute")##
$output.require("com.jaxio.jpa.querybyexample.MetamodelUtil")##

/**
 * Controller that allows you to manage an entity's x-to-many association.
 */
public abstract class $output.currentClass<E extends Identifiable<PK>, PK extends Serializable> {
    protected String labelKey;
    protected MessageUtil messageUtil;
    protected GenericController<E, PK> controller;
    protected GenericPermission<E> permission;
    protected GenericRepository<E, PK> repository;
    protected SelectableListDataModel<E> dataModel;
    protected final Collection<CascadeType> cascades;
    protected final boolean needEntityRemovalTracking;

    public ${output.currentClass}(List<E> elements, GenericController<E, PK> controller, PluralAttribute<?, ?, E> attribute) {
        this.dataModel = new SelectableListDataModel<E>(elements);
        this.labelKey = buildLabelKey(attribute);
        this.controller = controller;
        this.messageUtil = controller.getMessageUtil();
        this.permission = controller.getPermission();
        this.repository = controller.getRepository();
        this.cascades = MetamodelUtil.getInstance().getCascades(attribute);
        this.needEntityRemovalTracking = !MetamodelUtil.getInstance().isOrphanRemoval(attribute);
    }

    /**
     * Return the dataModel used in the datatable component. 
     */
    public SelectableListDataModel<E> getModel() {
        return dataModel;
    }

    /**
     * Set the dataModel used in the datatable component. 
     */
    public void setModel(SelectableListDataModel<E> dataModel) {
        this.dataModel = dataModel;
    }

    /**
     * Remove the passed entity from the x-to-many association.
     */
    protected abstract void remove(E e);

    /**
     * Add the passed entity to the x-to-many association.
     */
    protected abstract void add(E e);

    /**
     * Instantiate a new entity with a view to adding it to the x-to-many assocation.
     */
    protected E create() {
        E e = repository.getNewWithDefaults();
        onCreate(e);
        return e;
    }

    /**
     * Override this method if you need to perform additional initialization such as setting
     * the entity that owns the passed x-t-many association element.
     * This method is invoked by the create method.
     * Does nothing by default.
     */
    protected void onCreate(E e) {
    }

    /**
     * Action to edit the entity corresponding to the selected row.
     * @return the implicit jsf view.
     */
    public String edit() {
        return controller.editView(labelKey, dataModel.getSelectedRow(), editCallBack, isSubView());
    }

    protected ConversationCallBack<E> editCallBack = new ConversationCallBack<E>() {
        private static final long serialVersionUID = 1L;

        @Override
        protected void onSaved(E e){
            E previous = dataModel.getSelectedRow();
            // 'previous' is not necessarily the same instance as 'e', as 'e' may come form a merge... 
            // so we replace the old instance with the new one.
            remove(previous);
            add(e);
        }
        
        @Override
        protected void onOk(E e) {
            E previous = dataModel.getSelectedRow();
            // 'previous' is not necessarily the same instance as 'e', as 'e' may come form a merge... 
            // so we replace the old instance with the new one.
            remove(previous);
            add(e);
            messageUtil.infoEntity("status_edited_ok", e);
        }
    };

    /**
     * Action to view the entity corresponding to the selected row.
     * @return the implicit jsf view.
     */
    public String view() {
        return controller.editReadOnlyView(labelKey, dataModel.getSelectedRow(), isSubView());
    }

    /**
     * This datatable row selection listener invokes the {@link ${pound}edit()} or {@link ${pound}view()} action depending on the context
     * and force the navigation to the returned implicit view.
     * Use it from a p:ajax event="rowSelect".
     */
    public void onRowSelect(SelectEvent event) {
        if (getCurrentConversation().getCurrentContext().isReadOnly()) {        
            Faces.navigate(view());
        } else if (permission.canEdit(dataModel.getSelectedRow())) {
            Faces.navigate(edit());
        } else {
            Faces.navigate(view());
        }
    }

    /**
     * Remove the entity corresponding to the selected row from the x-to-many association.
     */
    public void remove() {
        if (!permission.canDelete(dataModel.getSelectedRow())) {
            messageUtil.error("error_action_denied");
            return;            
        }

        remove(dataModel.getSelectedRow());
        if(needEntityRemovalTracking){
            getCurrentConversation().getCurrentContext().addDependentEntity(dataModel.getSelectedRow());
        }
        messageUtil.infoEntity("status_removed_ok", dataModel.getSelectedRow());
    }

    /**
     * Action to create a new entity. The entity is not added a priori to the x-to-many association. It is added
     * if the <code>ok</code> callback is invoked. 
     * @return the implicit jsf view.
     */
    public String add() {
        return controller.createView(labelKey, create(), addCallBack, isSubView());
    }

    protected ConversationCallBack<E> addCallBack = new ConversationCallBack<E>() {
        private static final long serialVersionUID = 1L;

        @Override
        protected void onSaved(E e){
            add(e);
        }
        
        @Override
        protected void onOk(E e) {
            add(e);
            messageUtil.infoEntity("status_added_new_ok", e);
        }
    };

    public String search() {
        return select();
    }

    public String select() {
        return controller.multiSelectView(labelKey, selectCallBack, isSubView());
    }

    protected ConversationCallBack<E> selectCallBack = new ConversationCallBack<E>() {
        private static final long serialVersionUID = 1L;

        @Override
        protected void onSelected(E e) {
            if(!permission.canSelect(e)) {
                messageUtil.error("error_action_denied");
                return;
            }

            remove(e); // in case it was already selected.
            add(e);
            messageUtil.infoEntity("status_added_existing_ok", e);
        }
    };

    /**
     * @return <code>true</code> if view is related to a parent and should not be performed any persistance. <code>false</code> otherwise.
     */
    private boolean isSubView() {
        return cascades.contains(CascadeType.ALL) || cascades.contains(CascadeType.PERSIST);
    }
    
    private String buildLabelKey(PluralAttribute<?, ?, E> attribute) {
        return UPPER_CAMEL.to(LOWER_CAMEL, attribute.getDeclaringType().getJavaType().getSimpleName())
          + "_" + attribute.getName();
    }
}
