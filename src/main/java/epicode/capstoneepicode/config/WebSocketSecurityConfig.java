package epicode.capstoneepicode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

@Configuration
@EnableWebSocketSecurity
public class WebSocketSecurityConfig {

    @Bean
    AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        messages
//                .nullDestMatcher().authenticated()
                .simpDestMatchers("/chat/**").authenticated()
                .simpSubscribeDestMatchers("/chat/**", "/topic/messages").authenticated()
                .anyMessage().denyAll();

        return messages.build();
    }

}
