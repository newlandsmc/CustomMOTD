package com.semivanilla.custommotd.conversation.prompt;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CompletePrompt implements Prompt {

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        Conversable c = context.getForWhom();
        String title = (String) context.getSessionData( "title" );
        String line1 = (String) context.getSessionData( "line1" );
        String line2 = (String) context.getSessionData( "line2" );
        Boolean restricted = (Boolean) context.getSessionData("restricted");
        MOTDWrapper motdWrapper = new MOTDWrapper(title, line1, line2, restricted, false);
        CustomMOTD.getMotdManager().addMotd(motdWrapper);
        return "New motd created.";
    }

    @Override
    public boolean blocksForInput(@NotNull ConversationContext context) {
        return false;
    }

    @Override
    public @Nullable Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
        return END_OF_CONVERSATION;
    }
}
