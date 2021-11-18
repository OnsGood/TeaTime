package com.example.teatime.bot.statemachine.identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import com.example.teatime.bot.life.MessageDto;

/**
 * Класс для соотношения сообщений и способов реагировать на них.
 * Реализован как цепочка обязанностей, элемент которой в случае неудачи передает эстафету следующему элементу.
 */
public final class MessageIdentifier {
  private final List<Identifier> identifiers;

  public MessageIdentifier() {
    this.identifiers = new ArrayList<>();
  }

  public MessageIdentifier addIdentifier(Function<MessageDto, Boolean> identifier, Consumer<MessageDto> action) {
    identifiers.add(new Identifier(identifier, action));
    return this;
  }

  public MessageIdentifier addIdentifier(Identifier identifier) {
    identifiers.add(identifier);
    return this;
  }


  public void identifyMessage(MessageDto message) {
    for (Identifier identifier : identifiers) {
      boolean identify = identifier.identify(message);
      if (identify) {
        return;
      }
    }
  }

  public record Identifier(Function<MessageDto, Boolean> identifierFunction, Consumer<MessageDto> action) {
    public boolean identify(MessageDto message) {
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
