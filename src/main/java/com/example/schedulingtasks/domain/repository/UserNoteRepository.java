package com.example.schedulingtasks.domain.repository;

import com.example.schedulingtasks.domain.entity.UserNote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserNoteRepository extends JpaRepository<UserNote, Long> {

    @Query("select un from UserNote un " +
            "left join fetch un.note " +
            "left join fetch un.accessLevel " +
            "where " +
            "(un.user.id = :userId " +
            "and " +
            "(un.accessLevel.value = com.example.schedulingtasks.enums.AccessLevelEnum.OWNER " +
            "or un.accessLevel.value = com.example.schedulingtasks.enums.AccessLevelEnum.READ " +
            "or un.accessLevel.value = com.example.schedulingtasks.enums.AccessLevelEnum.WRITE) " +
            ") " +
            "or un.accessLevel.value = com.example.schedulingtasks.enums.AccessLevelEnum.PUBLIC_READ")
    @Transactional(readOnly = true)
    List<UserNote> retrieveAllByUser(final Long userId, final Pageable page);


    @Query("select distinct un from UserNote un " +
            "left join fetch un.note " +
            "where " +
            "(un.user.id = :userId and un.note.id in :noteIds " +
            "and " +
            "(un.accessLevel.value = com.example.schedulingtasks.enums.AccessLevelEnum.OWNER " +
            "or un.accessLevel.value = com.example.schedulingtasks.enums.AccessLevelEnum.READ " +
            "or un.accessLevel.value = com.example.schedulingtasks.enums.AccessLevelEnum.WRITE" +
            ")" +
            ") " +
            "or un.accessLevel.value = com.example.schedulingtasks.enums.AccessLevelEnum.PUBLIC_WRITE")
    @Transactional(readOnly = true)
    List<UserNote> retrieveAllByUserAndByNoteIds(final Long userId, final List<Long> noteIds);

    @Query("select un from UserNote un " +
            "left join fetch un.note " +
            "where un.user.id = :userId and un.note.id in :noteIds " +
            "and " +
            "(" +
            "select count(uno) from UserNote uno " +
            "where uno.user.id = :userId and un.note.id in :noteIds " +
            "and un.accessLevel.value = com.example.schedulingtasks.enums.AccessLevelEnum.OWNER  " +
            ") > 1 ")
//            "and un.accessLevel.value = com.example.schedulingtasks.enums.AccessLevelEnum.OWNER")
    @Transactional
    List<UserNote> retrieveAllByUserAndByNoteIdsAndByAccessLevelIsOwner(final Long userId, final List<Long> noteIds);


}
