package com.example.teatime.bot.statemachine.identifier;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Класс для соотношения сообщений и способов реагировать на них.
 * Реализован как цепочка обязанностей, элемент которой в случае неудачи передает эстафету следующему элементу.
 */
public final class MessageIdentifier {
  private final List<Identifier> identifiers;

  public MessageIdentifier() {
    this.identifiers = new ArrayList<>();
  }

  public MessageIdentifier addIdentifier(Function<Message, Boolean> identifier, Consumer<Message> action) {
    identifiers.add(new Identifier(identifier, action));
    return this;
  }

  public MessageIdentifier addIdentifier(Identifier identifier) {
    identifiers.add(identifier);
    return this;
  }


  public void identifyMessage(Message message) {
    for (Identifier identifier : identifiers) {
      boolean identify = identifier.identify(message);
      if (identify) {
        return;
      }
    }
  }

  public record Identifier(Function<Message, Boolean> identifierFunction, Consumer<Message> action) {
    public boolean identify(Message message) {
      boolean identify = Optional.ofNullable(identifierFunction.apply(message))
          .orElseThrow(() -> new RuntimeException("Identify expression result must be not null"));
      if (identify) {
        action.accept(message);
        return true;
      } else {
        return false;
      }
    }
  }

}
