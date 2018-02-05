package com.ots.dpel.auth.services.impl;

import com.ots.dpel.auth.core.domain.Role;
import com.ots.dpel.auth.core.domain.User;
import com.ots.dpel.auth.dto.RegisterDto;
import com.ots.dpel.auth.dto.UserRegistrationDto;
import com.ots.dpel.auth.persistence.DpUserRepository;
import com.ots.dpel.auth.persistence.RoleRepository;
import com.ots.dpel.auth.services.RegistrationService;
import com.ots.dpel.global.exceptions.DpValidationException;
import com.ots.dpel.global.utils.DpTextUtils;
import com.ots.dpel.global.utils.MessageSourceProvider;
import com.ots.dpel.management.core.domain.Contribution;
import com.ots.dpel.management.core.enums.ContributionStatus;
import com.ots.dpel.management.core.enums.ContributionType;
import com.ots.dpel.management.dto.ContributionDto;
import com.ots.dpel.management.dto.ElectionDepartmentInfoDto;
import com.ots.dpel.management.dto.ElectionProcedureDto;
import com.ots.dpel.management.persistence.ContributionRepository;
import com.ots.dpel.management.services.ContributionService;
import com.ots.dpel.management.services.ElectionDepartmentService;
import com.ots.dpel.management.services.ElectionProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    
    @Autowired
    private ElectionProcedureService electionProcedureService;
    
    @Autowired
    private ContributionService contributionService;
    
    @Autowired
    private ElectionDepartmentService electionDepartmentService;
    
    @Autowired
    private DpUserRepository dpUserRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private ContributionRepository contributionRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private MessageSourceProvider messageSourceProvider;
    
    @Override
    public UserRegistrationDto findUserRegistrationByIdentifier(String identifier) {
        
        ContributionDto contributionDto = contributionService.getContributionByIdentifier(identifier);
    
        if (contributionDto == null) {
            throw new DpValidationException("INVALID_IDENTIFIER",
                    messageSourceProvider.getMessage("error.user.registration.invalid.identifier"));
        }
        
        if (contributionDto.getStatus().equals(ContributionStatus.REGISTRATION_COMPLETED.name())) {
            throw new DpValidationException("REGISTRATION_COMPLETED",
                    messageSourceProvider.getMessage("error.user.registration.completed"));
        }
        
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        
        userRegistrationDto.setUsername(contributionDto.getVolunteerDto().getEmail());
        userRegistrationDto.setEmail(contributionDto.getVolunteerDto().getEmail());
        userRegistrationDto.setLastName(contributionDto.getVolunteerDto().getLastName());
        userRegistrationDto.setFirstName(contributionDto.getVolunteerDto().getFirstName());
        userRegistrationDto.setContributionType(ContributionType.valueOf(contributionDto.getType()).toString());
        
        if (contributionDto.getElectionDepartmentId() != null) {
            
            ElectionDepartmentInfoDto electionDepartmentInfo = electionDepartmentService.getElectionDepartmentInfo(contributionDto
                    .getElectionDepartmentId());
            
            userRegistrationDto.setElectionCenterId(electionDepartmentInfo.getElectionCenterId());
            userRegistrationDto.setElectionCenterName(electionDepartmentInfo.getElectionCenterName());
            userRegistrationDto.setElectionDepartmentId(contributionDto.getElectionDepartmentId());
            userRegistrationDto.setElectionDepartmentName(electionDepartmentInfo.getName());
        }
        
        return userRegistrationDto;
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public void registerUser(RegisterDto registerDto) {
    
        if (DpTextUtils.isEmpty(registerDto.getPassword()) || DpTextUtils.isEmpty(registerDto.getPasswordRepeat())) {
            throw new DpValidationException("EMPTY_PASSWORD",
                    messageSourceProvider.getMessage("error.user.registration.empty.password"));
        }
    
        if (!registerDto.getPassword().equals(registerDto.getPasswordRepeat())) {
            throw new DpValidationException("PASSWORDS_NOT_MATCH",
                    messageSourceProvider.getMessage("error.user.registration.passwords.not.match"));
        }
        
        ContributionDto contributionDto = contributionService.getContributionByIdentifier(registerDto.getIdentifier());
        ElectionProcedureDto electionProcedureDto = electionProcedureService.getCurrent();
    
        if (contributionDto == null) {
            throw new DpValidationException("INVALID_IDENTIFIER",
                    messageSourceProvider.getMessage("error.user.registration.invalid.identifier"));
        }
    
        String username = registerDto.getUsername();
        String email = contributionDto.getVolunteerDto().getEmail();
        if (username == null) {
            username = email;
        }
        
        // Έλεγχος ότι δεν υπάρχει ήδη αποθηκευμένος χρήστης με το συγκεκριμένο username
        if (dpUserRepository.findByUsernameAndAndElectionProcedureId(username, electionProcedureDto.getId()) != null) {
            throw new DpValidationException("USERNAME_EXISTS",
                    messageSourceProvider.getMessage("error.user.registration.username.exists"));
        }
        
        // Δημιουργία και αποθήκευση user
        User user = new User();
        
        user.setElectionProcedureId(electionProcedureDto.getId());
        user.setUsername(username);
        user.setPassword(passwordEncoder.encodePassword(registerDto.getPassword(), null));
        user.setEmail(email);
        user.setType("USER");
        user.setLastName(contributionDto.getVolunteerDto().getLastName());
        user.setFirstName(contributionDto.getVolunteerDto().getFirstName());
        user.setElectionDepartmentId(contributionDto.getElectionDepartmentId());
    
        contributionDto.setType(ContributionType.convertToEquivalent(contributionDto.getType()));
        
        List<Role> roles = roleRepository.findByCode(contributionDto.getType().toLowerCase());
    
        if (roles != null) {
            user.setRoleList(roles);
        }
        
        dpUserRepository.save(user);
    
        // Ανάκτηση και ενημέρωση εγγραφής σύνδεσης contribution
        Contribution contribution = contributionRepository.findOne(contributionDto.getId());
    
        contribution.setStatus(ContributionStatus.REGISTRATION_COMPLETED);
        contribution.setRegistrationDate(new Date());
        
        contributionRepository.save(contribution);
    }
}
