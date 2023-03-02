package comtaskxrXrTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import comtaskxrXrTask.model.UserMaster;

public interface AuthRepository extends JpaRepository<UserMaster, Integer> {

	public UserMaster findByUsername(String username);

	public Boolean existsByEmail(String email);

	public UserMaster findUserByEmail(String email);
}
