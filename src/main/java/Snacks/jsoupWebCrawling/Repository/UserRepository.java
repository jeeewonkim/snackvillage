package Snacks.jsoupWebCrawling.Repository;


import Snacks.jsoupWebCrawling.User.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(String userId);

    User findByUserEmail(String userEmail);

    Optional<User> findByUserEmailAndPlatformType(String userEmail, String platformType);


}