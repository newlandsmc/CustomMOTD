package com.semivanilla.custommotd.conversation;

import com.semivanilla.custommotd.CustomMOTD;
import com.semivanilla.custommotd.conversation.prompt.TitlePrompt;
import com.semivanilla.custommotd.manager.wrapper.MOTDWrapper;
import org.bukkit.conversations.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConversationManager implements ConversationAbandonedListener {

    public ConversationManager(JavaPlugin plugin, Conversable conversable) {
        ConversationFactory conversationFactory = new ConversationFactory(plugin)
                .withModality(true)
                .withFirstPrompt(new MotdPrompt())
                .withEscapeSequence("cancel");
        conversationFactory.buildConversation(conversable).begin();
    }

    @Override
    public void conversationAbandoned(@NotNull ConversationAbandonedEvent abandonedEvent) {
        abandonedEvent.getContext().getForWhom().sendRawMessage("Conversation ended.");
    }

    private class MotdPrompt extends FixedSetPrompt {
        public MotdPrompt() {
            super("continue", "cancel");
        }

        public String getPromptText(ConversationContext context) {
            return "You are about to create a new motd, you can cancel this at any time by typing cancel. enter continue to continue." + formatFixedSet();
        }

        @Override
        protected Prompt acceptValidatedInput(ConversationContext context, String input) {
            if (input.equalsIgnoreCase("cancel")) {
                return Prompt.END_OF_CONVERSATION;
            }
            return new TitlePrompt();
        }
    }


}
