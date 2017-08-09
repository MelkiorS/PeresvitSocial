package ua.peresvit.sn.service.registration;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;
import ua.peresvit.sn.domain.entity.User;

import java.util.Locale;

@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("serial")

@Data
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final User user;
    private final boolean isWithToken;
    private boolean isDone;
    private boolean isUpdatePassword;

    public OnRegistrationCompleteEvent(final User user, final Locale locale, final String appUrl, boolean isWithToken) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
        this.isWithToken = isWithToken;
        this.isDone = false;
        this.isUpdatePassword = false;
    }

    public OnRegistrationCompleteEvent(final User user, final Locale locale, final String appUrl, boolean isWithToken, boolean isUpdatePassword) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
        this.isWithToken = isWithToken;
        this.isDone = false;
        this.isUpdatePassword = isUpdatePassword;
    }

}
