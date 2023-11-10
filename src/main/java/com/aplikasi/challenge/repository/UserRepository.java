package com.aplikasi.challenge.repository;

import com.aplikasi.challenge.entity.OrderDetail;
import com.aplikasi.challenge.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID>, JpaSpecificationExecutor<Users> {
    @Query("select u from Users u WHERE u.id = :idUser")
    public Users getById(@Param("idUser") UUID idUser);

    @Query(value = "select count(u) from Users u WHERE u.username = :userName")
    public Long getByUsername(@Param("userName") String userName);

    @Query(value = "select count(u) from Users u WHERE u.emailAddress = :userEmail")
    public Long getByEmailAddress(@Param("userEmail") String userEmail);

    @Query(value = "SELECT od.* " +
            "FROM order_detail od " +
            "JOIN orders o ON od.order_id = o.id " +
            "JOIN users u ON o.user_id = u.id " +
            "WHERE u.id = :userId", nativeQuery = true)
    public List<OrderDetail> getListOrderDetail(@Param("userId") UUID userId);

//    @Query(value = "select u from Users u WHERE LOWER(u.username) like LOWER(:nameParam)")
//    public Page<Users> getLikeName(@Param("nameParam") String nameParam, Pageable pageable);
//
//    @Query(value = "select u from Users u")
//    public Page<Users> getAllUsers(Pageable pageable);
//
//    @Modifying
//    @Transactional
//    @Query(value = "UPDATE users u SET u.username = :#{#updated.username}, " +
//            "u.email_address = :#{#updated.emailAddress}, " +
//            "u.password = :#{#updated.password}" +
//            "WHERE u.id = :#{#updated.id}",
//            nativeQuery = true)
//    public void updateData(@Param("updated") Users updated);
//
//    @Query(value = "INSERT INTO users (username, email_address, password)" +
//            "VALUES (:#{#user.username}, :#{#user.emailAddress}, :#{#user.password})",
//            nativeQuery = true)
//    public Users saveData(@Param("user") Users user);
//
//    @Modifying
//    @Transactional
//    @Query(value = "UPDATE users u SET u.deleted_date = :currentDate" +
//            "WHERE u.id = :idUser",
//            nativeQuery = true)
//    public void deleteData(@Param("idUser") UUID idUser, @Param("currentDate") Date currentDate);
}
