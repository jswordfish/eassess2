package com.assessment.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assessment.data.User;
import com.assessment.data.UserType;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.email=:email and u.companyId=:companyId")
	User findByPrimaryKey(@Param("email") String email, @Param("companyId") String companyId);

	@Query("SELECT u FROM User u WHERE u.email=:email and u.companyId=:companyId")
	List<User> findByPrimaryKeyFromList(@Param("email") String email, @Param("companyId") String companyId);

	@Query("SELECT u FROM User u WHERE u.companyId=:companyId")
	List<User> findByCompany(@Param("companyId") String companyId);

	@Query("SELECT u FROM User u WHERE u.email=:email and u.password=:password and u.companyId=:companyId")
	User findByPrimaryKeyAndPassword(@Param("email") String email, @Param("password") String password,
			@Param("companyId") String companyId);

	@Query("SELECT q FROM User q WHERE q.companyId=:companyId and ( q.email LIKE %:searchText%  OR q.mobileNumber LIKE %:searchText%  OR q.firstName LIKE %:searchText%  OR q.lastName LIKE %:searchText%  OR q.department LIKE %:searchText% OR q.groupOfUser LIKE %:searchText% OR q.grade LIKE %:searchText%)")
	public List<User> searchQuestions(@Param("companyId") String companyId,
			@Param("searchText") String searchText);

	@Query("SELECT u FROM User u WHERE u.companyId=:companyId and u.userType=:userType")
	Page<User> findUsersByTypeAndCompany(@Param("companyId") String companyId,
			@Param("userType") UserType userType, Pageable pageable);

	@Query("SELECT concat( u.collegeName,' - ', u.grade,' - ', u.classifier) FROM User u WHERE u.companyId=:companyId and u.userType='LMS_STUDENT' and u.collegeName is not null  and u.collegeName=:collegeName and u.grade is not null and u.classifier is not null group by u.collegeName, u.grade, u.classifier")
	List<String> findInstituteGradeClassifier(@Param("companyId") String companyId,
			@Param("collegeName") String collegeName);

	@Query("SELECT u FROM User u WHERE u.companyId=:companyId and u.collegeName=:collegeName and u.grade=:grade and u.classifier=:classifier")
	List<User> findByInstituteGradeClassifier(@Param("companyId") String companyId,
			@Param("collegeName") String collegeName, @Param("grade") String grade,
			@Param("classifier") String classifier);

	@Query("SELECT u.email FROM User u WHERE u.companyId=:companyId and u.collegeName=:collegeName and u.grade=:grade and u.classifier=:classifier")
	List<String> findEmailByInstituteGradeClassifier(@Param("companyId") String companyId,
			@Param("collegeName") String collegeName, @Param("grade") String grade,
			@Param("classifier") String classifier);

	@Query("SELECT u FROM User u WHERE u.collegeName=:collegeName and u.companyId=:companyId and u.userType='LMS_STUDENT'")
	Page<User> getLmsStudentResults(@Param("collegeName") String collegeName,@Param("companyId") String companyId,Pageable pageable);
	@Query("SELECT u FROM User u WHERE u.userType='LMS_STUDENT'")
	List<User> findByUserType();
}