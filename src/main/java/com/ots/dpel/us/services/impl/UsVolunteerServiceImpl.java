package com.ots.dpel.us.services.impl;

import static com.ots.dpel.global.utils.ReflectionUtils.indexObjectsByFieldValue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.ots.dpel.auth.core.domain.User;
import com.ots.dpel.auth.dto.RegisterDto;
import com.ots.dpel.auth.persistence.DpUserRepository;
import com.ots.dpel.auth.services.RegistrationService;
import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.ext.dto.VolunteerDto;
import com.ots.dpel.ext.services.VolunteerService;
import com.ots.dpel.global.exceptions.DpValidationException;
import com.ots.dpel.global.utils.MessageSourceProvider;
import com.ots.dpel.global.utils.ReflectionUtils;
import com.ots.dpel.management.args.ContributionArgs;
import com.ots.dpel.management.core.enums.ContributionStatus;
import com.ots.dpel.management.core.enums.ContributionType;
import com.ots.dpel.management.core.enums.ElectionRound;
import com.ots.dpel.management.dto.ContributionDto;
import com.ots.dpel.management.dto.ElectionDepartmentBasicDto;
import com.ots.dpel.management.dto.ElectionDepartmentDto;
import com.ots.dpel.management.dto.list.ContributionListDto;
import com.ots.dpel.management.services.ContributionService;
import com.ots.dpel.management.services.ElectionDepartmentService;
import com.ots.dpel.us.args.UsVolunteerArgs;
import com.ots.dpel.us.dto.ManualUserCreationRequest;
import com.ots.dpel.us.dto.VolunteerAssignmentRequestDto;
import com.ots.dpel.us.dto.VolunteerReassignmentRequestDto;
import com.ots.dpel.us.dto.list.UsVolunteerListDto;
import com.ots.dpel.us.services.UsVolunteerService;

@Service
public class UsVolunteerServiceImpl implements UsVolunteerService {
    
    @Autowired
    private VolunteerService volunteerService;
    @Autowired
    private ContributionService contributionService;
    @Autowired
    private ElectionDepartmentService electionDepartmentService;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private DpUserRepository userRepository;
    @Autowired
    private MessageSourceProvider messageSourceProvider;
    
    @Override
    public void assignVolunteer(VolunteerAssignmentRequestDto assignment) {
        // TODO validate request
        Long electionDepartmentId = assignment.getElectionDepartmentId();
        Long volunteerId = assignment.getVolunteerId();
        ElectionRound round = assignment.getRound();
        
        // Έλεγχος για το αν ο εθελοντής έχει ενταχθεί ήδη σε τμήμα (δεν ισχύει για προέδρους και αναπληρωτές προέδρους)
        ElectionDepartmentDto electionDepartmentDto = this.electionDepartmentService.findElectionDepartment(electionDepartmentId);
        List<ElectionDepartmentBasicDto> volunteerDepartments = contributionService.getVolunteerElectionDepartments(volunteerId, round, null);
        
        for(ElectionDepartmentBasicDto volunteerDepartment: volunteerDepartments) {
            if(volunteerDepartment.getContributionType() == null || assignment.getContributionType() == null ||
                    !ContributionType.isLeader(volunteerDepartment.getContributionType()) || !ContributionType.isLeader(assignment.getContributionType())) {
                throw new DpValidationException("VOLUNTEER_ALREADY_ASSIGNED", messageSourceProvider.getMessage("users.error.volunteer.already.assigned"));
            }
        }
        
        // Πρετοιμασία νέου contribution
        VolunteerDto volunteerDto = this.volunteerService.findVolunteer(volunteerId);
        ContributionType contributionType = assignment.getContributionType();
        Long contributionCandidateId = contributionType.equals(ContributionType.CANDIDATE_REPRESENTATIVE) ? assignment.getContributionCandidateId() : null;
        
        ContributionDto newContributionDto = new ContributionDto();
        newContributionDto.setElectionDepartmentId(electionDepartmentId);
        newContributionDto.setRound(round.name());
        newContributionDto.setType(contributionType.name());
        newContributionDto.setCandidateId(contributionCandidateId);
        newContributionDto.setVolunteerId(volunteerId);
        newContributionDto.setVolunteerDto(volunteerDto);
        
        List<ContributionDto> contributionDtos = electionDepartmentDto.getContributionDtos();
        contributionDtos.add(newContributionDto);
        // Πρώτη αποθήκευση του τμήματος με το νέο contribution
        electionDepartmentDto = this.electionDepartmentService.saveElectionDepartment(electionDepartmentDto);
        newContributionDto = findContributionByVolunteerId(electionDepartmentDto, volunteerId);
        
        // Δημιουργία χρήστη ή e-mail ενεργοποίησης, και ανάλογη αλλαγή του contribution status 
        Boolean notify = assignment.getNotify();
        String status = newContributionDto.getStatus();
        if (notify != null && status != null && !ContributionStatus.WITHOUT_ACCESS.name().equals(status)) {
            if (Boolean.TRUE.equals(notify)) {
                // Αποστολή email για ενεργοποίηση χρήστη και αλλαγή status σε EMAIL_SENT 
                this.contributionService.notifyPendingContribution(
                    electionDepartmentId, newContributionDto.getId());
            } else {
                // Δημιουργία χρήστη με τα credentials που περιλαμβάνονται στο assignment
                createUserFromVolunteerAssignment(assignment, newContributionDto);
            }
        }
    }
    
    private ContributionDto findContributionByVolunteerId(
            ElectionDepartmentDto electionDepartmentDto, Long volunteerId) {
        List<ContributionDto> contributionDtos = electionDepartmentDto.getContributionDtos();
        for (ContributionDto contributionDto: contributionDtos) {
            if (contributionDto.getVolunteerId().equals(volunteerId)) {
                return contributionDto;
            }
        }
        return null;
    }
    
    @Override
    public void unassignVolunteer(Long volunteerId) {
        
        if (volunteerId == null) {
            return;
        }
        
        VolunteerDto volunteerDto = this.volunteerService.findVolunteer(volunteerId);
        if (volunteerDto == null) {
            return;
        }
        
        // Κατάργηση της σύνδεσης σε τμήμα(τα). 
        this.contributionService.deleteContributionByVolunteerId(volunteerId);
        
        // Σε περίπτωση που βρεθεί εγγραφή χρήστη του οποίου το username και το email είναι ίδια με το email του εθελοντή, 
        // θεωρούμε ότι "ανήκει" στον εθελοντή που μετακινείται, και επομένως διαγράφεται και ο χρήστης.
        String email = volunteerDto.getEmail();
        User user = this.userRepository.findByUsernameAndEmail(email);
        if (user != null) {
            this.userRepository.delete(user.getId());
        }
    }

    @Override
    public void notifyVolunteer(Long volunteerId) {
        this.contributionService.notifyVolunteer(volunteerId);
    }
    
    @Override
    public void notifyAll(boolean pending, boolean notified) {
        
        if (pending) {
            this.contributionService.notifyAllPendingContributions();
        }
        
        if (notified) {
            this.contributionService.renotifyAllPendingContributions();
        }
    }

    @Override
    public void manuallyCreateUser(ManualUserCreationRequest userCreation) {
        
        Long volunteerId = userCreation.getVolunteerId();
        ContributionDto contributionDto = this.contributionService.findContributionByVolunteerId(volunteerId);
        if (contributionDto == null) {
            return;
        }
        
        RegisterDto registerDto = new RegisterDto();
        registerDto.setIdentifier(contributionDto.getIdentifier());
        registerDto.setPassword(userCreation.getPassword());
        registerDto.setPasswordRepeat(userCreation.getPasswordConfirmation());
        
        registrationService.registerUser(registerDto);
    }

    private void createUserFromVolunteerAssignment(
            VolunteerAssignmentRequestDto assignment, ContributionDto contributionDto) {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setIdentifier(contributionDto.getIdentifier());
        registerDto.setPassword(assignment.getPassword());
        registerDto.setPasswordRepeat(assignment.getPasswordConfirmation());
        
        registrationService.registerUser(registerDto);
    }
    
    @Override
    public Page<UsVolunteerListDto> getUsVolunteerIndex(SearchableArguments arguments, Pageable pageable) {
        
        UsVolunteerArgs usVolunteerArgs = (UsVolunteerArgs) arguments;
        
        Boolean contributionAssigned = usVolunteerArgs.getContributionAssigned();
        
        // filter by last name, first name, eklSpecialNo
        List<VolunteerDto> volunteerDtos = this.volunteerService.findVolunteersBasic(usVolunteerArgs);
        
        ContributionArgs contributionArgs = usVolunteerArgs.toContributionArgs();
        // filter by geo data, election center/epartment, contribution type/status
        List<ContributionListDto> contributionDtos = 
            this.contributionService.findContributions(contributionArgs);
        
        List<UsVolunteerListDto> usVolunteerDtos = combineDtos(volunteerDtos, contributionDtos, contributionAssigned);
        
        sortVolunteerDtos(usVolunteerDtos, pageable);
        List<UsVolunteerListDto> usVolunteerDtosPage = pageVolunteerDtos(usVolunteerDtos, pageable);
        
        return new PageImpl<>(usVolunteerDtosPage, pageable, usVolunteerDtos.size());
    }

    @Override
    public void reassignVolunteer(VolunteerReassignmentRequestDto reassignment) {

        if (reassignment == null || !reassignment.checkValidity()) {
            return;
        }
        
        Long volunteerId = reassignment.getVolunteerId();
        Long fromDepartmentId = reassignment.getFromElectionDepartmentId();
        Long toDepartmentId = reassignment.getToElectionDepartmentId();
        String round = reassignment.getRound();
        
        // Αναζήτηση των μελών του εκλογικού τμήματος με id = fromDepartmentId
        List<ContributionDto> contributionDtos = 
            this.contributionService.getContributionsByElectionDepartment(fromDepartmentId);
        for (ContributionDto contributionDto: contributionDtos) {
            if (!contributionDto.getRound().equals(round) || !contributionDto.getVolunteerId().equals(volunteerId)) {
                continue;
            }
            // Αλλαγή Ε.Τ. του εθελοντή
            contributionDto.setElectionDepartmentId(toDepartmentId);
            VolunteerDto volunteerDto = contributionDto.getVolunteerDto();
            this.contributionService.saveContribution(contributionDto);
            
            // Σε περίπτωση που βρεθεί εγγραφή χρήστη του οποίου το username και το email είναι ίδια με το email του εθελοντή, 
            // θεωρούμε ότι "ανήκει" στον εθελοντή που μετακινείται, και επομένως μετακινείται και ο χρήστης.
            String email = volunteerDto.getEmail();
            String contributionStatus = contributionDto.getStatus();
            if (StringUtils.isNotBlank(email) && ContributionStatus.REGISTRATION_COMPLETED.name().equals(contributionStatus)) {
                User user = this.userRepository.findByUsernameAndEmail(email);
                if (user != null) {
                    user.setElectionDepartmentId(toDepartmentId);
                    this.userRepository.save(user);
                }
            }
        }
    }

    private List<UsVolunteerListDto> pageVolunteerDtos(List<UsVolunteerListDto> usVolunteerDtos, Pageable pageable) {
        int offset = pageable.getOffset();
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int fromIndex = offset;
        int toIndex = Math.min((pageNumber + 1) * pageSize , usVolunteerDtos.size());
       
        return usVolunteerDtos.subList(fromIndex, toIndex);
    }
    
    private void sortVolunteerDtos(List<UsVolunteerListDto> usVolunteerDtos, Pageable pageable) {
        Order order = getSortOrder(pageable);
        if (order == null || usVolunteerDtos.size() == 1) {
            return;
        }
        
        Comparator<UsVolunteerListDto> comparator = createVolunteerComparator(order);
        Collections.sort(usVolunteerDtos, comparator);
    }
    
    private Comparator<UsVolunteerListDto> createVolunteerComparator(Order order) {
        
        final String sortFieldName = order != null? order.getProperty(): null;
        final Direction dir = order != null? order.getDirection(): null;
        final boolean ascending = dir != null? dir.equals(Direction.ASC): true;
        final Map<String, Field> usVolunteerListDtoFields = ReflectionUtils.getFieldsMap(UsVolunteerListDto.class);
        final Field sortField = usVolunteerListDtoFields.get(sortFieldName);
        final Class<?> sortFieldClass = sortField.getType();
        final boolean isSortFieldComparable = Comparable.class.isAssignableFrom(sortFieldClass);
        
        return new Comparator<UsVolunteerListDto>() {

            @SuppressWarnings({ "unchecked", "rawtypes" })
            @Override public int compare(UsVolunteerListDto dto1, UsVolunteerListDto dto2) {
                
                Object dto1Prop = ReflectionUtils.getFieldValue(dto1, sortFieldName, sortFieldClass);
                Object dto2Prop = ReflectionUtils.getFieldValue(dto2, sortFieldName, sortFieldClass);
                
                int ascMultiplier = (ascending? 1: -1);
                if (isSortFieldComparable) {
                    Comparable c1 = (Comparable) dto1Prop;
                    Comparable c2 = (Comparable) dto2Prop;
                    return ascMultiplier * ObjectUtils.compare(c1, c2);
                } else {
                    if (dto1Prop == null && dto2Prop == null) {
                        return 0;
                    } else if (dto1Prop == null) {
                        return ascMultiplier * (-1);
                    } else if (dto2Prop == null) {
                        return ascMultiplier;
                    } else {
                        return ascMultiplier * (dto1Prop.hashCode() - dto2.hashCode());
                    }
                }
            }
        };
    }
    
    private Order getSortOrder(Pageable pageable) {
        Order order = null;
        Sort sort = pageable.getSort();
        if (sort != null) {
            Iterator<Order> it = sort.iterator();
            if (it != null && it.hasNext()) {
                order = it.next();
            }
        }
        
        return order;
    }
    
    private List<UsVolunteerListDto> combineDtos(List<VolunteerDto> volunteerDtos, List<ContributionListDto> contributionDtos, Boolean assigned) {
        List<UsVolunteerListDto> usVolunteerDtos = new ArrayList<>();
        
        Map<Long, ContributionListDto> contributionDtosByVolunteerId = 
            indexObjectsByFieldValue(contributionDtos, "volunteerId", Long.class);

        for (VolunteerDto volunteerDto: volunteerDtos) {
            Long volunteerId = volunteerDto.getId();
            UsVolunteerListDto usVolunteerDto = new UsVolunteerListDto(volunteerDto);
            ContributionListDto contributionDto = contributionDtosByVolunteerId.get(volunteerId);
            
            if (contributionDto != null && !Boolean.FALSE.equals(assigned)) {
                usVolunteerDto.setContributionDto(contributionDto);
            }
            
            // assigned == null: όλοι οι εθελοντές της λίστας
            // assigned == true: μόνο οι εθελοντές που έχουν ανατεθεί σε τμήμα
            // assigned == false: μόνο οι εθελοντές που ΔΕΝ έχουν ανατεθεί σε τμήμα
            if (assigned == null || 
                (assigned.booleanValue() && contributionDto != null) ||
                (!assigned.booleanValue() && contributionDto == null)) {
                usVolunteerDtos.add(usVolunteerDto);
            }
        }
        
        return usVolunteerDtos;
    }
}
