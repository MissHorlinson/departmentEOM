package com.departmenteom.service;

import com.departmenteom.entity.GroupInfo;
import com.departmenteom.entity.GroupStream;
import com.departmenteom.entity.Student;
import com.departmenteom.entity.dictionary.Cipher;
import com.departmenteom.repo.GroupInfoRepo;
import com.departmenteom.repo.GroupStreamRepo;
import com.departmenteom.repo.dictionary.CipherRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupInfoService {

    private final GroupInfoRepo groupInfoRepo;
    private final GroupStreamRepo groupStreamRepo;

    private final CipherRepo cipherRepo;

    public List<GroupInfo> getAllGroup() {
        return groupInfoRepo.findAll();
    }


    public List<Student> getGroupList(Long id) {
        Optional<GroupInfo> groupInfoOpt = groupInfoRepo.findById(id);

        if (groupInfoOpt.isPresent() && groupInfoOpt.get().getStudentSet().size() > 0) {
            List<Student> studentList = new ArrayList<>(groupInfoOpt.get().getStudentSet());
            return studentList.stream().sorted(Comparator.comparing(Student::getLastName)
                    .thenComparing(Student::getFirstName)
                    .thenComparing(Student::getSecondName)).collect(Collectors.toList());
        } else {
            log.warn("Group {} not fount in db", id);
            return Collections.emptyList();
        }
    }

    public GroupInfo saveGroup(GroupInfo groupInfo, Long cipherId) {
        log.info("Crate new group for cipher id {} and year {}", cipherId, groupInfo.getAdmissionYear());
        Optional<GroupStream> groupStreamOpt = groupStreamRepo.getByStreamPlanInfoPlanCipherIdAndStreamPlanInfoAdmissionYear(cipherId, groupInfo.getAdmissionYear());
        Cipher cipher = cipherRepo.getById(cipherId);
        groupInfo.setGroupCipher(cipher);

        if (groupStreamOpt.isPresent()) {
            groupInfo.setGroupInfoStream(groupStreamOpt.get());
        } else {
            GroupStream gs = groupStreamRepo.save(new GroupStream());
            groupInfo.setGroupInfoStream(gs);
        }
        log.info("Created");
        return groupInfoRepo.save(groupInfo);
    }

    public GroupInfo getGroupById(Long id) {
        log.info("Search group by id {}", id);
        Optional<GroupInfo> groupInfoOpt = groupInfoRepo.findById(id);
        return groupInfoOpt.orElse(null);
    }

    public Optional<GroupInfo> getGroupByYearAndCipherId(LocalDateTime year, long cipherId) {
        log.info("Check if group exist for {} and cipher id {}", year.getYear(), cipherId);
        return groupInfoRepo.getFirstByAdmissionYearAndGroupCipherId(year, cipherId);
    }
}