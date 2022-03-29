package uz.pdp.market.telegram.handlers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class UpdateHandler {

    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;



    public void handle(Update update) {

        if (update.hasMessage()) {
            messageHandler.handle(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            callbackHandler.handle(update.getCallbackQuery());
        } else System.out.println("Not Found");
    }


}
