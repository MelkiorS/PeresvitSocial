package ua.peresvit.sn.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.*;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.peresvit.sn.domain.dto.UserDto;
import ua.peresvit.sn.domain.entity.User;
import ua.peresvit.sn.error.UserAlreadyExistException;
import ua.peresvit.sn.service.UserService;
import ua.peresvit.sn.service.registration.OnRegistrationCompleteEvent;
import ua.peresvit.sn.util.helper.SocialMediaService;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    private final UserService userService;

    private final ApplicationEventPublisher eventPublisher;

    private final ConnectionRepository connectionRepository;

    private final MessageSource messages;

    private final ProviderSignInUtils providerSignInUtils;

    @Autowired
    public RegistrationController(UserService userService, ApplicationEventPublisher eventPublisher, ConnectionRepository connectionRepository, MessageSource messages, ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
        this.connectionRepository = connectionRepository;
        this.messages = messages;
        this.providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository);
    }

    // Show registration form
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model){
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        UserDto userDto;
        userDto = createUserDto(connection);
        model.addAttribute("user", userDto);
        return "home";
    }

    private UserDto createUserDto(Connection<?> connection) {
        UserDto userDto = new UserDto();

        if (connection != null) {
            switch (connection.getKey().getProviderId()) {
                case "facebook" :
                    userDto = createSocialUserDtoForFB((Connection<Facebook>) connection);
                    break;
/*                case "google" :
                    userDto = createSocialUserDtoForGoogle((Connection<Google>) connection);
                    break;
                case "vkontakte" :
                    userDto = createSocialUserDtoForVK((Connection<VKontakte>) connection);
                    break;
*/
            }
        }
        return userDto;
    }

    // register user
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String registerUserAccount(
            @ModelAttribute("user") UserDto accountDto, final HttpServletRequest servletRequest,
            RedirectAttributes model, Locale locale, WebRequest webRequest) {

        User registered = createUserAccount(accountDto);
        if (registered == null) {
            if (accountDto.getSocial() != null) {
                registered = userService.findUserByEmail(accountDto.getEmail());
                if (registered.getPassword().equals(accountDto.getPassword())) {
                    switch (accountDto.getSocial()) {
                        case FACEBOOK: {
                            registered.setProfileFB(accountDto.getProfileFB());
                            break;
                        }
                        case VKONTAKTE: {
                            registered.setProfileVK(accountDto.getProfileVK());
                            break;
                        }
                        case GOOGLE:{
                            registered.setProfileGoogle(accountDto.getProfileGoogle());
                            break;
                        }
                    }
                    userService.save(registered);
                    authenticateUser(registered);
                    providerSignInUtils.doPostSignUp(registered.getEmail(), webRequest);
                    return "redirect:/home/workField";
                }
            }
            model.addFlashAttribute("message", messages.getMessage("message.regError", null, locale));
            return "redirect:/home";
        }
        registered.setEnabled(true);
        userService.save(registered);
        if (accountDto.getSocial() != null) {
            providerSignInUtils.doPostSignUp(registered.getEmail(), webRequest);
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, locale, getAppUrl(servletRequest), false));
            authenticateUser(registered);
            return "redirect:/home/workField";
        }
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, locale, getAppUrl(servletRequest), true));
        model.addFlashAttribute("message", messages.getMessage("message.regConfirm", null, locale));
        return "redirect:/home";
    }

    // remind user
    @RequestMapping(value = "/remind", method = RequestMethod.GET)
    public String remindGOToEmailPage(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "home/enterEmail";
    }

    @RequestMapping(value = "/remind", method = RequestMethod.POST)
    public String remindUserAccount(
            @ModelAttribute("user") UserDto accountDto, final HttpServletRequest request) {

        User registered = userService.findUserByEmail(accountDto.getEmail());
        if (registered == null) {
            return "home";
        }
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request), true, true));
        return "redirect:/";
    }


//    validate token
    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration(final Locale locale, final Model model, @RequestParam("token") final String token) throws UnsupportedEncodingException {
        final String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            final User user = userService.getUser(token);
//            Here info about successful verification added to message attribute)
            model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
            authenticateUser(user);
            return "redirect:/home/workField";
        }
//        Show unsuccessful operation on the main page
        model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
        return "home";
    }

    // remindConfirm
    @RequestMapping(value = "/remindConfirm", method = RequestMethod.GET)
    public String remindConfirmRegistration(final Locale locale, final Model model, @RequestParam("token") final String token) throws UnsupportedEncodingException {
        final String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            final User user = userService.getUser(token);
            authenticateUser(user);
            model.addAttribute("user", user);
            return "/home/changePass";
        }

        model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
        return "home";
    }

    // remindConfirm Post
    @RequestMapping(value = "/remindConfirm", method = RequestMethod.POST)
    public String remindConfirmRegistrationPost(User user) {
        User editUser = userService.findOne(user.getUserId());
        editUser.setPassword(user.getPassword());
        userService.save(editUser);

        return "redirect:/home";
    }

//    -----------------------------------------------------------------------------------------------------------------

    private User createUserAccount(UserDto accountDto) {
        User registered;
        try {
            registered = userService.registerNewUserAccount(accountDto);
        } catch (UserAlreadyExistException e) {
            return null;
        }
        return registered;
    }

//Creation DTO from connection with FB
    private UserDto createSocialUserDtoForFB(Connection<Facebook> connection) {
        Facebook facebook = connection.getApi();
//      Here we can get fields from FB connection
// Fields available for FB
//{ "id", "about", "age_range", "birthday", "context", "cover", "currency", "devices", "education", "email", "favorite_athletes", "favorite_teams", "first_name", "gender", "hometown", "inspirational_people", "installed", "install_type", "is_verified", "languages", "last_name", "link", "locale", "location", "meeting_for", "middle_name", "name", "name_format", "political", "quotes", "payment_pricepoints", "relationship_status", "religion", "security_settings", "significant_other", "sports", "test_group", "timezone", "third_party_id", "updated_time", "verified", "video_upload_limits", "viewer_can_send_gift", "website", "work"}

        String [] fields = { "id", "email",  "first_name", "last_name", "link"};
        org.springframework.social.facebook.api.User userProfile = facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, fields);
        UserDto userDto = new UserDto();
        userDto.setEmail(userProfile.getEmail());
        userDto.setFirstName(userProfile.getFirstName());
        userDto.setLastName(userProfile.getLastName());
        userDto.setProfileFB(userProfile.getLink());
        userDto.setSocial(SocialMediaService.FACEBOOK);
        return userDto;
    }
/*

//Creation DTO from connection with VK
    private UserDto createSocialUserDtoForVK(Connection<VKontakte> connection) throws ClientException, ApiException {
        UserProfile socialMediaProfile = connection.fetchUserProfile();
        VKontakte vKontakte = connection.getApi();
//            Here we can get fields from VK connection
        UserDto userDto = new UserDto();
        userDto.setFirstName(socialMediaProfile.getFirstName());
        userDto.setLastName(socialMediaProfile.getLastName());
        userDto.setEmail(vKontakte.getEmail());
        userDto.setProfileVK("id"+connection.getKey().getProviderUserId());
        userDto.setSocial(SocialMediaService.VKONTAKTE);
        return userDto;
    }

//Creation DTO from connection with Google
    private UserDto createSocialUserDtoForGoogle(Connection<Google> connection) {
        Google google = connection.getApi();
//            Here we can get fields from Google connection
        Person googleProfile = google.plusOperations().getGoogleProfile();
        UserDto userDto = new UserDto();
        userDto.setEmail(googleProfile.getAccountEmail());
        userDto.setFirstName(googleProfile.getGivenName());
        userDto.setLastName(googleProfile.getFamilyName());
        userDto.setProfileGoogle(googleProfile.getUrl());
        userDto.setSocial(SocialMediaService.GOOGLE);
        return userDto;
    }
*/

    private void authenticateUser(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword(),
                        authorities));
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
