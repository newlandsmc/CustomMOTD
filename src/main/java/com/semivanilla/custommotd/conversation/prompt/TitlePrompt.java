package com.semivanilla.custommotd.conversation.prompt;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TitlePrompt extends ValidatingPrompt {

    @Override
    protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String input) {
        for (MOTDWrapper wrapper : CustomMOTD.getMotdManager().getMotds()) {
            if(wrapper.getTitle().equalsIgnoreCase(input)) {
                context.getForWhom().sendRawMessage("There is already a motd with this title.");
                return false;
            }
        }
        return true;
    }

    @Override
    protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
        context.setSessionData("title", input);
        return new Line1Prompt();
    }

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return "What is the title for this new motd?";
    }
}
