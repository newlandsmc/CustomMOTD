package com.semivanilla.custommotd.conversation.prompt;

import org.bukkit.conversations.BooleanPrompt;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RestrictedPrompt extends BooleanPrompt {

    @Override
    protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, boolean input) {
        context.setSessionData("restricted", input);
        if (!input) {
            context.setSessionData("weight", 0);
            return new CompletePrompt();
        }
        return new WeightPrompt();
    }

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return "Should this be a restricted motd?";
    }
}
