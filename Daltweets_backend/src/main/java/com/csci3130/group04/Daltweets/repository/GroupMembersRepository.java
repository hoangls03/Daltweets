package com.csci3130.group04.Daltweets.repository;

import com.csci3130.group04.Daltweets.model.Group;
import com.csci3130.group04.Daltweets.model.GroupMembers;
import com.csci3130.group04.Daltweets.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GroupMembersRepository extends JpaRepository<GroupMembers,Integer> {
    @Query("SElECT gm FROM GroupMembers gm WHERE gm.user.username = :name")
    List<GroupMembers> findGroupMembersByUsername(@Param("name") String username);
    @Query("SELECT gm FROM GroupMembers gm WHERE gm.user.username = :name AND gm.group.name = :group")
    GroupMembers findGroupMembersByUserAndGroup(@Param("name") String username, @Param("group") String groupname );

    @Query("Select gm.user FROM GroupMembers gm WHERE gm.group.name = :name")
    List<User> findGroupMembersByGroupName(@Param("name") String groupName);

    @Query("SELECT gm.user FROM GroupMembers gm WHERE gm.group.name = :name  AND gm.isAdmin = true")
    List<User> findAdminsByGroupName(@Param("name") String groupName);
}
