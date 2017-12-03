package ua.peresvit.sn.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.peresvit.sn.config.Constant;
import ua.peresvit.sn.domain.dao.CombatArtReppository;
import ua.peresvit.sn.domain.dao.RoleRepository;
import ua.peresvit.sn.domain.dao.UserRepository;
import ua.peresvit.sn.domain.dao.VerificationTokenRepository;
import ua.peresvit.sn.domain.dto.UserDto;
import ua.peresvit.sn.domain.entity.Role;
import ua.peresvit.sn.domain.entity.User;
import ua.peresvit.sn.domain.entity.UserGroup;
import ua.peresvit.sn.domain.entity.VerificationToken;
import ua.peresvit.sn.error.UserAlreadyExistException;
import ua.peresvit.sn.service.UserService;

import java.util.*;

//import org.bionic.dao.VerificationTokenRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CombatArtReppository combatArtReppository;

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    @Override
    public <S extends User> S save(S arg0) {
        return userRepository.save(arg0);
    }

    @Override
    public User findOne(Long userId) {
        User user = userRepository.findOne(userId);
//        initializeUserInfo(user);
        return user;
    }

    @Override
    public User findByName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllWithoutCurrent() {
        List<User> list = userRepository.findAll();
        list.remove(getCurrentUser());
        return list;
    }

    @Override
    public List<User> findByRole(Role role) {
        return userRepository.findByRoles(role);
    }

    @Override
    public void activateUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void deactivateUser(User user) {
//        final VerificationToken verificationToken = tokenRepository.findByUser(user);
//
//        if (verificationToken != null) {
//            tokenRepository.delete(verificationToken);
//        }
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public boolean equals(Object obj) {
        return userRepository.equals(obj);
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User with email '" + email + "' not found."));
//        initializeUserInfo(user);
        return user;
    }

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        return userRepository.findByEmail(name).orElseThrow(()->new UsernameNotFoundException("User with email '" + name + "' not found."));
    }

  /*  @Override
    public void initializeUserInfo(User user) {
        if(user == null)
            return;
    }
*/

    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public User getUser(String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        userRepository.save(user);
        return TOKEN_VALID;
    }

    @Override
    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    @Transactional
    @Override
    public User registerNewUserAccount(final UserDto accountDto) {
        if (emailExist(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
        }
        final User user = new User();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(accountDto.getPassword());
        user.setEmail(accountDto.getEmail());
        // Here the role USER is set to new user AS DEFAULT
        user.setRoles(Arrays.asList(roleRepository.findByRoleName("USER"))); // TODO refactor to multi roles
        user.setAvatarURL("http://image.flaticon.com/icons/svg/126/126486.svg");
        if (accountDto.getProfileFB() != null) {
            user.setProfileFB(accountDto.getProfileFB());
        }
        if (accountDto.getProfileVK() != null) {
            user.setProfileVK(accountDto.getProfileVK());
        }
        if (accountDto.getProfileGoogle() != null) {
            user.setProfileGoogle(accountDto.getProfileGoogle());
        }

//        user.setCombatArt(combatArtReppository.findOneMessage(1L));
        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public List<UserGroup> getUserGroups(User user) {
        return userRepository.getUserGroups(user);
    }

    @Override
    public Set<User> getSetFromStringArray(String[] users) {
        Set<User> res = new HashSet<>();
        if (users != null && users.length > 0) res.addAll(Arrays.asList(getArrayFromStringArray(users)));
        return res;
    }

    @Override
    public User[] getArrayFromStringArray(String[] users) {
        int len = users == null ? 0 : users.length;
        User[] res = new User[len];
        for(int i = 0; i < len;i++)
            res[i] = userRepository.findOne(Long.parseLong(users[i]));
        return res;
    }

    @Override
	public String saveFile(User user, MultipartFile inputFile) {

        // return old avatar
		if(inputFile.isEmpty())
			return user.getAvatarURL();
			
		if(user.getUserId() == null)
			save(user);
			
		String pathFile = Constant.UPLOAD_PATH + "/" + Constant.USR_FOLDER + user.getUserId() + "/" + Constant.AVATAR;
		String fileURL = Constant.uploadingFile(inputFile, pathFile);
		
		// delete old file
		if(user.getUserId() != null){
			String oldPath = findOne(user.getUserId()).getAvatarURL();
			if(!fileURL.equals(oldPath))
				Constant.deleteFile(findOne(user.getUserId()).getAvatarURL());
		}
			
		return fileURL;
	}


    @Override
    public List<User> getGroupsUsers(UserGroup[] ug) {
        return userRepository.getGroupsUsers(ug);
    }

    @Override
    public List<User> getGroupsUsersWithoutCurrent(UserGroup[] ug) {
        List<User> res = getGroupsUsers(ug);
        res.remove(getCurrentUser());
        return res;
    }
}
