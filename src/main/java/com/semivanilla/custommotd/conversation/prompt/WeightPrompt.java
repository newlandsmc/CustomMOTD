package com.semivanilla.custommotd.conversation.prompt;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WeightPrompt extends NumericPrompt {

    @Override
    protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull Number input) {
        context.setSessionData("weight", input);
        return new CompletePrompt();
    }

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return "What should the weight be for this motd?";
    }
}
