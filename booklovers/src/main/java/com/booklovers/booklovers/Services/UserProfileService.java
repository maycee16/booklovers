package com.booklovers.booklovers.Services;


import com.booklovers.booklovers.Cloudinary.CloudinaryService;
import com.booklovers.booklovers.Entity.UserProfile;
import com.booklovers.booklovers.Entity.Users;
import com.booklovers.booklovers.Repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import com.booklovers.booklovers.Repository.UsersRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UsersRepository userRepository;
    private final CloudinaryService cloudinaryService;

    public UserProfileService(
            UserProfileRepository userProfileRepository,
            UsersRepository userRepository,
            UsersRepository usersRepository,
            CloudinaryService cloudinaryService) {

        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public UserProfile createProfile(
            Long userId,
            MultipartFile profilePicture,
            String phoneNumber,
            String city,
            String county,
            String status,
            String category) throws Exception {

        // Find the user
        Users user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Check if profile already exists
        if (userProfileRepository.findByUserId(userId).isPresent()) {
            throw new RuntimeException("Profile already exists");
        }

        // Upload image to Cloudinary
        String imageUrl = null;

        if (profilePicture != null && !profilePicture.isEmpty()) {
            imageUrl = cloudinaryService.uploadImage(profilePicture);
        }

        // Create profile
        UserProfile profile = new UserProfile();

        profile.setUser(user);
        profile.setProfilePicture(imageUrl);
        profile.setPhoneNumber(phoneNumber);
        profile.setCity(city);
        profile.setCounty(county);
        profile.setStatus(status);
        profile.setCategory(category);

        return userProfileRepository.save(profile);
    }

    public UserProfile getProfileByUserId(Long userId) {
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found"));
    }

    public UserProfile updateProfile(
            Long userId,
            String phoneNumber,
            String city,
            String county,
            String status,
            String category) {

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found"));

        profile.setPhoneNumber(phoneNumber);
        profile.setCity(city);
        profile.setCounty(county);
        profile.setStatus(status);
        profile.setCategory(category);

        return userProfileRepository.save(profile);
    }

    public void deleteProfile(Long userId) {

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found"));

        userProfileRepository.delete(profile);
    }
}

    

