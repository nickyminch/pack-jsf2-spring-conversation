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
$output.java($WebConversationComponent, "ConversationBreadCrumb")##

$output.requireStatic($WebConversation, "ConversationHolder.getCurrentConversation")##
$output.requireStatic("org.apache.commons.lang.StringUtils.isNotBlank")##
$output.require("java.util.Iterator")##
$output.require("java.util.Stack")##
$output.require("org.primefaces.model.menu.DefaultMenuItem")##
$output.require("org.primefaces.model.menu.DynamicMenuModel")##
$output.require("org.primefaces.model.menu.MenuModel")##

$output.require($WebConversation, "Conversation")##
$output.require($WebConversation, "ConversationContext")##

/**
 * The Conversation breadcrumb displays the current conversation contextes.
 * Usage: &lt;breadcrumb model="${pound}{conversationBreadCrumb.model}" rendered="${pound}{conversationBreadCrumb.render}" /&gt;
 */
$output.dynamicAnnotationTakeOver("javax.inject.Named","javax.inject.Singleton")##
public class $output.currentClass {
    
    public boolean getRender() {
        Conversation currentConversation = getCurrentConversation();
        return currentConversation == null ? false : !currentConversation.getConversationContextes().isEmpty();
    }

    public MenuModel getModel() {
        MenuModel model = new DynamicMenuModel();
        Stack<ConversationContext<?>> ctxStack = getCurrentConversation().getConversationContextes();
        int beforeLastIndex = ctxStack.size() - 2;
        Iterator<ConversationContext<?>> iterator = ctxStack.iterator();

        // first item is rendered as ui-icon-home => we don't want it so we disable it.
        DefaultMenuItem menuItem = new DefaultMenuItem();
        menuItem.setRendered(false);
        model.addElement(menuItem);

        int i = 0;
        while (iterator.hasNext()) {
            ConversationContext<?> ctx = iterator.next();
            if (isNotBlank(ctx.getLabel())) {
                menuItem = new DefaultMenuItem();
                menuItem.setValue(ctx.getLabel());
                if (i == beforeLastIndex && beforeLastIndex > 0) {
                    // calls back button action which will trigger the callback 
                    // as if the user had pressed on 'back' button.
                    menuItem.setOnclick("APP.menu.back()");
                    menuItem.setImmediate(true);
                } else {
                    menuItem.setDisabled(true);
                }

                model.addElement(menuItem);
            }
            i++;
        }
        return model;
    }
}
