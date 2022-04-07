package uz.pdp.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.market.entity.auth.AuthUser;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long>, AbstractRepository {

    @Query(value = "from AuthUser a where a.chatId=:id")
    AuthUser findByChatId(@Param("id") String id);

    @Modifying
    @Transactional
    @Query("update AuthUser u set u.state=:state where u.chatId=:chatId")
    void changeStatus(@Param("chatId") String chatId, @Param("state") String state);


    Optional<AuthUser> findAuthUserByUserName(String userName);

}
