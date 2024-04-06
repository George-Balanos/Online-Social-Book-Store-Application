package app.bookstore.socialbookstore;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import app.bookstore.socialbookstore.domain.UserProfile;
import app.bookstore.socialbookstore.mappers.UserProfileMapper;
import java.util.Optional;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MappersTests {

    @Autowired
    private UserProfileMapper userProfileMapper;

    private UserProfile user;

    @Before
    public void setUp() {
        user = new UserProfile("Mpal", "George Mpal", 21);
        userProfileMapper.save(user);
    }

    @Test
    public void testSaveUserProfile() {
        Optional<UserProfile> userProfileOptional = userProfileMapper.findByFullName("George Mpal");
        assertTrue(userProfileOptional.isPresent()); // Check if UserProfile is present
        
        UserProfile userProfile = userProfileOptional.get();
        assertEquals("George Mpal", userProfile.getFullName()); // Compare the full name
        assertEquals(21, userProfile.getUserAge());
        
    }

}