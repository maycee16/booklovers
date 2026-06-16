package com.booklovers.booklovers.Contoller;


import com.booklovers.booklovers.Entity.UserProfile;
import com.booklovers.booklovers.Services.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profiles")
@CrossOrigin(origins = "*")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping
    public ResponseEntity<UserProfile> createProfile(
            @RequestParam Long userId,
            @RequestParam(required = false) MultipartFile profilePicture,
            @RequestParam String phoneNumber,
            @RequestParam String city,
            @RequestParam String county,
            @RequestParam String status,
            @RequestParam String category) throws Exception {

        UserProfile profile = userProfileService.createProfile(
                userId,
                profilePicture,
                phoneNumber,
                city,
                county,
                status,
                category
        );

        return ResponseEntity.ok(profile);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfile> getProfile(
            @PathVariable Long userId) {

        UserProfile profile =
                userProfileService.getProfileByUserId(userId);

        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserProfile> updateProfile(
            @PathVariable Long userId,
            @RequestParam String phoneNumber,
            @RequestParam String city,
            @RequestParam String county,
            @RequestParam String status,
            @RequestParam String category) {

        UserProfile profile =
                userProfileService.updateProfile(
                        userId,
                        phoneNumber,
                        city,
                        county,
                        status,
                        category
                );

        return ResponseEntity.ok(profile);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteProfile(
            @PathVariable Long userId) {

        userProfileService.deleteProfile(userId);

        return ResponseEntity.ok("Profile deleted successfully");
    }
}
    

